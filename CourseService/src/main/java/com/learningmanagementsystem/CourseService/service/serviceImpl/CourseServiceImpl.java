package com.learningmanagementsystem.CourseService.service.serviceImpl;

import com.learningmanagementsystem.CourseService.model.Course;
import com.learningmanagementsystem.CourseService.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {
    @Override
    public void createCourse(Course course, String userId) {

    }

    @Override
    public List<Course> getAllCourses() {
        return null;
    }

    @Override
    public List<Course> getAllCourseByTeacher(String userId) {
        return null;
    }

    @Override
    public Course editCourse(Course course, String courseId) {
        return null;
    }

    @Override
    public void deleteCourse(String courseId) {

    }

    @Override
    public Course getCourseInfo(String courseId) {
        return null;
    }
}
