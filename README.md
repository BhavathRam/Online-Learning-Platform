# Online Learning Platform

`online-learning-platform` is a full-stack course enrollment application with a Java 17 / Spring Boot REST API, Angular client, JWT authentication, role-based admin tools, and MySQL persistence.

## Features

- Student self-registration and sign-in. Passwords are BCrypt-hashed; the API issues signed, expiring JWTs.
- Searchable, filterable course catalog and course details, backed by MySQL APIs.
- Course enrollment with both application-level checks and a database unique constraint to reject duplicates.
- Student profile read/update and personal course list.
- Admin-only dashboard, course create/edit/delete, student directory, enrollment report, and per-course enrollment totals.
- Angular Router, reactive forms, HTTP client, JWT interceptor, authentication and admin route guards.
- Request validation, centralized API errors, OpenAPI / Swagger UI, and JUnit/Mockito service tests.

## Architecture

```text
Angular pages → Angular ApiService → JWT interceptor → REST controllers
                                                     ↓
                                  Spring Security JWT filter + role checks
                                                     ↓
                                     Controller → Service → Repository
                                                     ↓
                                        JPA / Hibernate → MySQL
```

Backend packages follow `controller`, `service`, `repository`, `entity`, `dto`, `security`, `config`, and `exception`. API requests and responses use DTOs; persistence entities are not serialized directly.

## Requirements

- Java 17 or later
- Maven 3.9+
- Node.js 20+ and npm
- Docker Desktop (recommended) or a local MySQL 8.0+ server

## Run the project

1. Start MySQL from the project root:

   ```sh
   cp .env.example .env  # PowerShell: Copy-Item .env.example .env
   # Edit .env and replace the placeholder with a unique local password.
   docker compose up -d mysql
   ```

   Compose creates database `online_learning` and publishes MySQL on port 3306. `.env` supplies the local MySQL root password and is ignored by Git.

2. Start the backend in another terminal:

   ```sh
   cd backend
   # PowerShell example for the local compose credentials and a development JWT key:
   $env:DB_USERNAME = 'root'
   $env:DB_PASSWORD = 'the-same-password-you-set-in-dot-env'
   $env:JWT_SECRET = 'replace-this-with-a-random-secret-at-least-32-characters-long'
   $env:ADMIN_EMAIL = 'admin@example.com'
   $env:ADMIN_PASSWORD = 'set-a-unique-password-of-at-least-12-characters'
   mvn spring-boot:run
   ```

   Bash users can set the same variables with `export`. The API is at `http://localhost:8080`; Hibernate creates/updates the three tables on startup. `ADMIN_EMAIL`, a unique `ADMIN_PASSWORD` (at least 12 characters), and a strong `JWT_SECRET` are required to provision the initial admin account and start the API. Do not commit these values.

3. Start the Angular client:

   ```sh
   cd frontend
   npm install
   npm start
   ```

   Open `http://localhost:4200`. The API base URL is `http://localhost:8080/api`; adjust it in `frontend/src/app/api.service.ts` if needed. Set `CORS_ALLOWED_ORIGIN` to the deployed frontend origin when hosting elsewhere.

## Database schema

Hibernate creates these tables from JPA entities:

- `users`: `id`, `name`, unique `email`, BCrypt `password`, `role` (`STUDENT` or `ADMIN`), and `created_at`.
- `courses`: `id`, `title`, `instructor`, `category`, `level`, `duration`, optional `image_url`, `description`, and `created_at`.
- `enrollments`: `id`, foreign keys `user_id` → `users.id` and `course_id` → `courses.id`, and `enrolled_at`.
- `enrollments` has unique constraint `uk_enrollment_user_course` over `(user_id, course_id)`.

For a production deployment, replace `spring.jpa.hibernate.ddl-auto=update` with versioned migrations such as Flyway or Liquibase.

## API endpoints

All protected endpoints require `Authorization: Bearer <JWT>`. Public course reads and auth endpoints do not.

| Method | Endpoint | Access | Purpose |
| --- | --- | --- | --- |
| POST | `/api/auth/register` | Public | Create a student account and return a JWT |
| POST | `/api/auth/login` | Public | Authenticate student/admin and return JWT |
| POST | `/api/auth/logout` | Authenticated client | Stateless logout acknowledgement; client discards JWT |
| GET | `/api/courses?search=&category=&level=` | Public | Browse/filter courses |
| GET | `/api/courses/{id}` | Public | Course detail |
| POST | `/api/courses` | Admin | Create course |
| PUT | `/api/courses/{id}` | Admin | Update course |
| DELETE | `/api/courses/{id}` | Admin | Delete course (blocked by existing enrollments) |
| GET | `/api/users/me` | Authenticated | Read current profile |
| PUT | `/api/users/me` | Authenticated | Update current profile |
| GET | `/api/users` | Admin | List students with enrollment totals |
| POST | `/api/enrollments/{courseId}` | Student | Enroll current student; duplicate returns HTTP 409 |
| GET | `/api/enrollments/me` | Student | Current student's courses |
| GET | `/api/enrollments` | Admin | All enrollment records |
| GET | `/api/admin/dashboard` | Admin | Student, course, and enrollment totals |

Swagger UI: `http://localhost:8080/swagger-ui.html` · OpenAPI JSON: `http://localhost:8080/v3/api-docs`.

## Admin account

An admin is provisioned on API startup from `ADMIN_EMAIL` and `ADMIN_PASSWORD` if that email does not exist. The public registration endpoint always creates `STUDENT` accounts; clients cannot self-assign the admin role. Sign in through the same login endpoint and Angular routes resolve admin access from the returned role. Provide these values and a strong `JWT_SECRET` via the environment or secret manager; the application has no built-in admin credentials or JWT signing key.

## Tests and builds

Backend unit tests use JUnit 5 and Mockito:

```sh
cd backend
mvn test
mvn package
```

Frontend production build:

```sh
cd frontend
npm ci
npm run build
```
