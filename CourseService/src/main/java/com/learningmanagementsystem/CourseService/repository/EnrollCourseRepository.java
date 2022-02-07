package com.learningmanagementsystem.CourseService.repository;

import com.learningmanagementsystem.CourseService.model.EnrollCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollCourseRepository extends JpaRepository<EnrollCourse, String> {
}
