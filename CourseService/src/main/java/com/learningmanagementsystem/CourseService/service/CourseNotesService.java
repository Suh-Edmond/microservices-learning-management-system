package com.learningmanagementsystem.CourseService.service;

import com.learningmanagementsystem.CourseService.dto.payload.CourseNotePayload;
import com.learningmanagementsystem.CourseService.dto.payload.CoursePayload;
import com.learningmanagementsystem.CourseService.model.CourseNote;

import java.util.List;

public interface CourseNotesService {

    public void CreateCourseNote(String courseId, CourseNotePayload courseNotePayload);
    public List<CourseNote> getCourseNotes(String courseId);
    public CourseNote getCourseNote(String courseId, String courseNoteId);
}
