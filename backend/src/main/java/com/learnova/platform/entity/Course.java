package com.learnova.platform.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.Instant;
@Entity @Table(name="courses") public class Course {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank @Column(nullable=false,length=180) private String title;
 @NotBlank @Column(nullable=false,length=120) private String instructor;
 @NotBlank @Column(nullable=false,length=80) private String category;
 @NotBlank @Column(nullable=false,length=30) private String level;
 @NotBlank @Column(nullable=false,length=50) private String duration;
 @Column(length=700) private String imageUrl;
 @NotBlank @Column(nullable=false,length=3000) private String description;
 @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 protected Course(){}
 public Course(String title,String instructor,String category,String level,String duration,String imageUrl,String description){this.title=title;this.instructor=instructor;this.category=category;this.level=level;this.duration=duration;this.imageUrl=imageUrl;this.description=description;}
 public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getInstructor(){return instructor;} public void setInstructor(String v){instructor=v;} public String getCategory(){return category;} public void setCategory(String v){category=v;} public String getLevel(){return level;} public void setLevel(String v){level=v;} public String getDuration(){return duration;} public void setDuration(String v){duration=v;} public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public Instant getCreatedAt(){return createdAt;}
}
