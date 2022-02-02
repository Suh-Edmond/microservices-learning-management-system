package com.learningmanagementsystem.CourseService.repository;

import com.learningmanagementsystem.CourseService.model.CourseSyllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSyllabusRepository extends JpaRepository<CourseSyllabus, String> {
}
