package com.learningmanagementsystem.UserService.service;

import com.learningmanagementsystem.UserService.model.User;

import java.util.List;

public interface UserService {

    public List<User> getAllTeachers();
    public List<User> getAllStudents();
    public List<User> getAllStudentWithInfoEnrolledCourse(List<String> studentIds);
    public void deleteUser(String userId, String role);
    public User getUser(String userId, String role);


}
