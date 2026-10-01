package com.learnova.platform.service;
import com.learnova.platform.dto.CourseDtos.*;
import com.learnova.platform.entity.Course;
import com.learnova.platform.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
@Service @Transactional(readOnly=true) public class CourseService {
 private final CourseRepository courses;private final EnrollmentRepository enrollments;
 public CourseService(CourseRepository c,EnrollmentRepository e){courses=c;enrollments=e;}
 public List<CourseResponse> browse(String category,String level,String query){return courses.browse(blank(category),blank(level),blank(query)).stream().map(this::map).toList();}
 public CourseResponse get(Long id){return map(find(id));}
 @Transactional public CourseResponse create(CourseRequest r){Course c=new Course(r.title(),r.instructor(),r.category(),r.level(),r.duration(),r.imageUrl(),r.description());return map(courses.save(c));}
 @Transactional public CourseResponse update(Long id,CourseRequest r){Course c=find(id);c.setTitle(r.title());c.setInstructor(r.instructor());c.setCategory(r.category());c.setLevel(r.level());c.setDuration(r.duration());c.setImageUrl(r.imageUrl());c.setDescription(r.description());return map(courses.save(c));}
 @Transactional public void delete(Long id){if(!courses.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Course not found");courses.deleteById(id);}
 public Course find(Long id){return courses.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Course not found"));}
 public CourseResponse map(Course c){return new CourseResponse(c.getId(),c.getTitle(),c.getInstructor(),c.getCategory(),c.getLevel(),c.getDuration(),c.getImageUrl(),c.getDescription(),c.getCreatedAt(),enrollments.countByCourseId(c.getId()));}
 private String blank(String s){return s==null||s.isBlank()?null:s.trim();}
}
