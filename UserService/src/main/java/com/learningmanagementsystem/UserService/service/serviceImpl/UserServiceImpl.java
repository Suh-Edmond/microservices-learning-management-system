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
    @Autowired
    CourseServiceImpl courseService;

    @Override
    public List<User> getAllTeachers() {
        List<User> users = this.userRepository.
                findAll().
                stream().
                filter(user -> user.getRole().equals(ERole.ROLE_TEACHER.toString())).
                collect(Collectors.toList());
        return users;
    }

    @Override
    public List<User> getAllStudents() {
        List<User> users = this.userRepository.
                findAll().
                stream().
                filter(user -> user.getRole().equals(ERole.ROLE_STUDENT.toString())).
                collect(Collectors.toList());
        return users;
    }

    @Override
    public List<User> getAllStudentWithInfoEnrolledCourse(String courseId) {
        List<String> studentIds = this.courseService.getAllStudentsEnrolledToCourseFromCourseService(courseId);
        List<User> students = studentIds.stream().map(id -> this.getStudent(id)).collect(Collectors.toList());
        return students;
    }

    @Override
    public void deleteUser(String userId, String role) {
        User user = this.getUser(userId, role);
        this.userRepository.delete(user);
    }

    @Override
    public User getUser(String userId, String role) {
        Optional<User> user = this.userRepository.findAll().stream().filter(user1 -> user1.getId().equals(userId) && user1.getRole().equals(role)).findFirst();
        user.orElseThrow(() -> new NotFoundException("Resource not found"));
        return user.get();
    }

    public User getStudent(String userId){
        Optional<User> user = this.userRepository.findAll().
                stream().
                filter(user1 -> user1.getId().equals(userId) && user1.getRole().equals(ERole.ROLE_STUDENT.toString())).
                findFirst();
        user.orElseThrow(() -> new NotFoundException("Resource not found"));
        return user.get();
    }

    @Override
    public User getTeacher(String userId) {
        Optional<User> user = this.userRepository.findAll().
                stream().
                filter(user1 -> user1.getId().equals(userId) && user1.getRole().equals(ERole.ROLE_TEACHER.toString())).
                findFirst();
        user.orElseThrow(() -> new NotFoundException("Resource not found"));
        return user.get();
    }
}
