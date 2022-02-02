package com.learningmanagementsystem.CourseService.repository;

import com.learningmanagementsystem.CourseService.model.CourseNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseNotesRepository extends JpaRepository<CourseNote, String> {
}
