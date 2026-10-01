package com.learnova.platform.repository;
import com.learnova.platform.entity.Course;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
public interface CourseRepository extends JpaRepository<Course,Long>{
 @Query("select c from Course c where (:category is null or lower(c.category)=lower(:category)) and (:level is null or lower(c.level)=lower(:level)) and (:q is null or lower(c.title) like lower(concat('%',:q,'%')) or lower(c.instructor) like lower(concat('%',:q,'%')) or lower(c.description) like lower(concat('%',:q,'%')))")
 List<Course> browse(@Param("category") String category,@Param("level") String level,@Param("q") String query);
}
