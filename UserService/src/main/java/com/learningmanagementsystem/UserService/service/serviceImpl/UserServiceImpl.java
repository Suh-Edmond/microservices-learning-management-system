package com.learningmanagementsystem.UserService.service.serviceImpl;

import com.learningmanagementsystem.UserService.exception.NotFoundException;
import com.learningmanagementsystem.UserService.model.ERole;
import com.learningmanagementsystem.UserService.model.User;
import com.learningmanagementsystem.UserService.repository.UserRepository;
import com.learningmanagementsystem.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllTeachers() {
        List<User> users = this.userRepository.
                findAll().
                stream().
                filter(user -> user.getRole().equals(ERole.USER_ROLES.get("teacher"))).
                collect(Collectors.toList());
        return users;
    }

    @Override
    public List<User> getAllStudents() {
        List<User> users = this.userRepository.
                findAll().
                stream().
                filter(user -> user.getRole().equals(ERole.USER_ROLES.get("student"))).
                collect(Collectors.toList());
        return users;
    }

    @Override
    public List<User> getAllStudentEnrolledCourse(List<String> studentIds) {
        List<User> students = studentIds.stream().map(id -> this.getUser(id)).collect(Collectors.toList());
        return students;
    }

    @Override
    public void deleteUser(String userId) {
        User user = this.getUser(userId);
        this.userRepository.delete(user);
    }

    @Override
    public User getUser(String userId) {
        Optional<User> user = this.userRepository.findById(userId);
        user.orElseThrow(() -> new NotFoundException("Resource not found"));
        return user.get();
    }
}
