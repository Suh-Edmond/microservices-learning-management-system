package com.learningmanagementsystem.CourseService.service;

import com.learningmanagementsystem.CourseService.dto.CourseSyllabusDto;
import com.learningmanagementsystem.CourseService.dto.payload.CourseSyllabusPayload;
import com.learningmanagementsystem.CourseService.model.CourseSyllabus;

import java.util.List;

public interface CourseSyllabusService {

    public void createCourseSyllabus(String courseId, CourseSyllabusPayload courseSyllabusPayload);
    public List<CourseSyllabusDto> getCourseSyllabuses(String courseId);
    public CourseSyllabusDto getCourseSyllabus(String courseId, String syllabusId);

}
