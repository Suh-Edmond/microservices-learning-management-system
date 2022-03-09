package com.learningmanagementsystem.CourseService.service;

import com.learningmanagementsystem.CourseService.dto.UserDto;

import java.util.List;

public interface UserService {

    public List<String> getAllStudentsEnrollCourse(String courseId);
    public UserDto getStudentFromUserService(String userId);
    public UserDto getTeacherFromUserService(String userId);
}
