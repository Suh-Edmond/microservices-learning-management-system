package com.learningmanagementsystem.UserService.service;

import com.learningmanagementsystem.UserService.model.User;

import java.util.List;

public interface UserService {

    public List<User> getAllTeachers();
    public List<User> getAllStudents();
    public List<User> getAllStudentEnrolledCourse(List<String> studentIds);//need to pass a list of
    // strings representing all the students from the course service
    public void deleteUser(String userId);
    public User getUser(String userId);


}
