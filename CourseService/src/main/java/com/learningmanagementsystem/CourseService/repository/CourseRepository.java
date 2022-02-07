package com.learningmanagementsystem.CourseService.repository;

import com.learningmanagementsystem.CourseService.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
}
