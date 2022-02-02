package com.learningmanagementsystem.CourseService.repository;

import com.learningmanagementsystem.CourseService.model.TeachCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeachCourseRepository extends JpaRepository<TeachCourse, String> {
}
