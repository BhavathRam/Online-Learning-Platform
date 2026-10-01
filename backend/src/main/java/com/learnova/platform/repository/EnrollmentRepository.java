package com.learnova.platform.repository;
import com.learnova.platform.entity.Enrollment;
import org.springframework.data.jpa.repository.*;
import java.util.*;
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long>{boolean existsByUserIdAndCourseId(Long userId,Long courseId);List<Enrollment> findByUserIdOrderByEnrolledAtDesc(Long userId);@EntityGraph(attributePaths={"user","course"}) List<Enrollment> findAllByOrderByEnrolledAtDesc();long countByCourseId(Long courseId);long countByUserId(Long userId);}
