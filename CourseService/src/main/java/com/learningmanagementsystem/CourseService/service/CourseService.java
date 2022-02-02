package com.learningmanagementsystem.CourseService.service;

import com.learningmanagementsystem.CourseService.model.Course;

import java.util.List;

public interface CourseService {

    public void createCourse(Course course, String userId);
    public List<Course> getAllCourses();
    public List<Course> getAllCourseByTeacher(String userId);
    public Course editCourse(Course course, String courseId, String userId);
    public void deleteCourse(String courseId, String userId);
    public Course getCourse(String courseId);
    public Course getTeacherCourse(String courseId, String userId);
}
