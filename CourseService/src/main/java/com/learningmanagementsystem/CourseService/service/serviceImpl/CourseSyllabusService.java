package com.learningmanagementsystem.CourseService.service.serviceImpl;

import com.learningmanagementsystem.CourseService.dto.payload.CourseSyllabusPayload;
import com.learningmanagementsystem.CourseService.model.CourseSyllabus;

import java.util.List;

public interface CourseSyllabusService {

    public void createCourseSyllabus(String courseId, CourseSyllabusPayload courseSyllabusPayload);
    public List<CourseSyllabus> getCourseSyllabuses(String courseId);
    public CourseSyllabus getCourseSyllabus(String courseId, String syllabusId);

}
