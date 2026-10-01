package com.learnova.platform.service;
import com.learnova.platform.dto.CourseDtos.CourseResponse;
import com.learnova.platform.dto.EnrollmentDtos.EnrollmentResponse;
import com.learnova.platform.entity.*;
import com.learnova.platform.repository.EnrollmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class) class EnrollmentServiceTest {
 @Mock EnrollmentRepository enrollments;@Mock CourseService courses;@Mock CurrentUserService current;
 @InjectMocks EnrollmentService service;
 private User learner(){User u=new User("Jamie","jamie@example.com","encoded",Role.STUDENT);ReflectionTestUtils.setField(u,"id",7L);return u;}
 private Course course(){Course c=new Course("Java Basics","Alex","Development","Beginner","6 hours",null,"Learn Java fundamentals.");ReflectionTestUtils.setField(c,"id",12L);return c;}
 @Test void duplicateEnrollmentReturnsConflict(){User user=learner();when(current.get()).thenReturn(user);when(courses.find(12L)).thenReturn(course());when(enrollments.existsByUserIdAndCourseId(7L,12L)).thenReturn(true);ResponseStatusException error=assertThrows(ResponseStatusException.class,()->service.enroll(12L));assertEquals(409,error.getStatusCode().value());verify(enrollments,never()).saveAndFlush(any());}
 @Test void createsAnEnrollmentForCurrentStudent(){User user=learner();Course course=course();Enrollment enrollment=new Enrollment(user,course);CourseResponse response=new CourseResponse(12L,"Java Basics","Alex","Development","Beginner","6 hours",null,"Learn Java fundamentals.",Instant.now(),0);
  when(current.get()).thenReturn(user);when(courses.find(12L)).thenReturn(course);when(enrollments.existsByUserIdAndCourseId(7L,12L)).thenReturn(false);when(enrollments.saveAndFlush(any(Enrollment.class))).thenReturn(enrollment);when(courses.map(course)).thenReturn(response);
  EnrollmentResponse result=service.enroll(12L);assertEquals(7L,result.userId());assertEquals(12L,result.course().id());verify(enrollments).saveAndFlush(any(Enrollment.class));}
}
