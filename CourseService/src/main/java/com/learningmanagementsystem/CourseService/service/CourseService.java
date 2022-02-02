package com.learningmanagementsystem.CourseService.service;

import com.learningmanagementsystem.CourseService.model.Course;

import java.util.List;

public interface CourseService {

    public void createCourse(Course course, String userId);
    public List<Course> getAllCourses();
    public List<Course> getAllCourseByTeacher(String userId);
    public Course editCourse(Course course, String courseId);
    public void deleteCourse(String courseId);
    public Course getCourseInfo(String courseId);
}
