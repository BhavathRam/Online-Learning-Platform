package com.learnova.platform.entity;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="enrollments",uniqueConstraints=@UniqueConstraint(name="uk_enrollment_user_course",columnNames={"user_id","course_id"})) public class Enrollment {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="user_id",nullable=false) private User user;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="course_id",nullable=false) private Course course;
 @Column(nullable=false,updatable=false) private Instant enrolledAt=Instant.now();
 protected Enrollment(){} public Enrollment(User user,Course course){this.user=user;this.course=course;}
 public Long getId(){return id;} public User getUser(){return user;} public Course getCourse(){return course;} public Instant getEnrolledAt(){return enrolledAt;}
}
