package com.learnova.platform.dto;
import java.time.Instant;
public final class EnrollmentDtos {private EnrollmentDtos(){}
 public record EnrollmentResponse(Long id,Long userId,String studentName,String studentEmail,CourseDtos.CourseResponse course,Instant enrolledAt){}
 public record DashboardResponse(long totalStudents,long totalCourses,long totalEnrollments){}
}
