export interface User { id:number; name:string; email:string; role:'STUDENT'|'ADMIN'; }
export interface AuthResponse { token:string; tokenType:string; user:User; }
export interface Course { id:number; title:string; instructor:string; category:string; level:string; duration:string; imageUrl:string; description:string; createdAt:string; enrollmentCount:number; }
export interface Enrollment { id:number; userId:number; studentName:string; studentEmail:string; course:Course; enrolledAt:string; }
export interface Dashboard { totalStudents:number; totalCourses:number; totalEnrollments:number; }
export type CoursePayload=Pick<Course,'title'|'instructor'|'category'|'level'|'duration'|'imageUrl'|'description'>;
