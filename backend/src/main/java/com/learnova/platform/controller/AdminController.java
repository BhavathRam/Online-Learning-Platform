package com.learnova.platform.controller;
import com.learnova.platform.dto.EnrollmentDtos.DashboardResponse;
import com.learnova.platform.entity.Role;
import com.learnova.platform.repository.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController {
 private final UserRepository users;private final CourseRepository courses;private final EnrollmentRepository enrollments;
 public AdminController(UserRepository u,CourseRepository c,EnrollmentRepository e){users=u;courses=c;enrollments=e;}
 @GetMapping("/dashboard") DashboardResponse dashboard(){return new DashboardResponse(users.countByRole(Role.STUDENT),courses.count(),enrollments.count());}
}
