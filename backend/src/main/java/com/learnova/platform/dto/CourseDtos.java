package com.learnova.platform.dto;
import jakarta.validation.constraints.*;
import java.time.Instant;
public final class CourseDtos {private CourseDtos(){}
 public record CourseRequest(@NotBlank @Size(max=180) String title,@NotBlank @Size(max=120) String instructor,@NotBlank @Size(max=80) String category,@NotBlank @Size(max=30) String level,@NotBlank @Size(max=50) String duration,@Size(max=700) String imageUrl,@NotBlank @Size(max=3000) String description){}
 public record CourseResponse(Long id,String title,String instructor,String category,String level,String duration,String imageUrl,String description,Instant createdAt,long enrollmentCount){}
}
