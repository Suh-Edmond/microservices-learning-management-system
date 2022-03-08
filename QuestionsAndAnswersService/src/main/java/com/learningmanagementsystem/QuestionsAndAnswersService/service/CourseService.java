package com.learningmanagementsystem.QuestionsAndAnswersService.service;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.CourseDto;

public interface CourseService {
    public CourseDto getCourseInfoFromCourseService(String courseId);
    public CourseDto findCourseByTitle(String title);


}
