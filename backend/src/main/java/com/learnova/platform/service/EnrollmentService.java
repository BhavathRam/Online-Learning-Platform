package com.learnova.platform.service;
import com.learnova.platform.dto.CourseDtos.CourseResponse;
import com.learnova.platform.dto.EnrollmentDtos.EnrollmentResponse;
import com.learnova.platform.entity.*;
import com.learnova.platform.repository.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
@Service public class EnrollmentService {
 private final EnrollmentRepository enrollments;private final CourseService courseService;private final CurrentUserService current;
 public EnrollmentService(EnrollmentRepository e,CourseService c,CurrentUserService u){enrollments=e;courseService=c;current=u;}
 @Transactional public EnrollmentResponse enroll(Long courseId){User user=current.get();Course course=courseService.find(courseId);if(enrollments.existsByUserIdAndCourseId(user.getId(),courseId))throw new ResponseStatusException(HttpStatus.CONFLICT,"Already enrolled in this course");try{return map(enrollments.saveAndFlush(new Enrollment(user,course)));}catch(DataIntegrityViolationException e){throw new ResponseStatusException(HttpStatus.CONFLICT,"Already enrolled in this course");}}
 @Transactional(readOnly=true) public List<EnrollmentResponse> mine(){return enrollments.findByUserIdOrderByEnrolledAtDesc(current.get().getId()).stream().map(this::map).toList();}
 @Transactional(readOnly=true) public List<EnrollmentResponse> all(){return enrollments.findAllByOrderByEnrolledAtDesc().stream().map(this::map).toList();}
 private EnrollmentResponse map(Enrollment e){Course c=e.getCourse();CourseResponse cr=courseService.map(c);return new EnrollmentResponse(e.getId(),e.getUser().getId(),e.getUser().getName(),e.getUser().getEmail(),cr,e.getEnrolledAt());}
}
