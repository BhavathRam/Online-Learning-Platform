package com.learnova.platform.controller;
import com.learnova.platform.dto.CourseDtos.*;
import com.learnova.platform.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/courses") public class CourseController {
 private final CourseService courses;public CourseController(CourseService c){courses=c;}
 @GetMapping List<CourseResponse> browse(@RequestParam(required=false) String category,@RequestParam(required=false) String level,@RequestParam(required=false) String search){return courses.browse(category,level,search);}
 @GetMapping("/{id}") CourseResponse get(@PathVariable Long id){return courses.get(id);}
 @PreAuthorize("hasRole('ADMIN')") @PostMapping CourseResponse create(@Valid @RequestBody CourseRequest r){return courses.create(r);}
 @PreAuthorize("hasRole('ADMIN')") @PutMapping("/{id}") CourseResponse update(@PathVariable Long id,@Valid @RequestBody CourseRequest r){return courses.update(id,r);}
 @PreAuthorize("hasRole('ADMIN')") @DeleteMapping("/{id}") void delete(@PathVariable Long id){courses.delete(id);}
}
