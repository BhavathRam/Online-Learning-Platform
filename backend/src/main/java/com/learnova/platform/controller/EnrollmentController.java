package com.learnova.platform.controller;
import com.learnova.platform.dto.EnrollmentDtos.EnrollmentResponse;
import com.learnova.platform.service.EnrollmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/enrollments") public class EnrollmentController {
 private final EnrollmentService enrollments;public EnrollmentController(EnrollmentService e){enrollments=e;}
 @PreAuthorize("hasRole('STUDENT')") @PostMapping("/{courseId}") EnrollmentResponse enroll(@PathVariable Long courseId){return enrollments.enroll(courseId);}
 @PreAuthorize("hasRole('STUDENT')") @GetMapping("/me") List<EnrollmentResponse> mine(){return enrollments.mine();}
 @PreAuthorize("hasRole('ADMIN')") @GetMapping List<EnrollmentResponse> all(){return enrollments.all();}
}
