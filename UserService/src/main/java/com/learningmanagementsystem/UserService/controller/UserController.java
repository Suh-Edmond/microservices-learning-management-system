package com.learningmanagementsystem.UserService.controller;


import com.learningmanagementsystem.UserService.Utils.Utils;
import com.learningmanagementsystem.UserService.dto.UserDto;
import com.learningmanagementsystem.UserService.model.ERole;
import com.learningmanagementsystem.UserService.model.User;
import com.learningmanagementsystem.UserService.service.serviceImpl.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/USER-SERVICE/api/v1/protected/")
@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    ModelMapper modelMapper;
    private Utils utils = new Utils();


    @GetMapping("teachers-all")
    public ResponseEntity<List<UserDto>> getAllTeachers(){
        List<User> users = this.userService.getAllTeachers();
        List<UserDto> userDtos = this.utils.getListUserDto(users);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);

    }

    @GetMapping("students-all")
    public ResponseEntity<List<UserDto>> getStudents() {
        List<User> users = this.userService.getAllStudents();
        List<UserDto> userDtos = this.utils.getListUserDto(users);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @DeleteMapping("users")
    public ResponseEntity<?>  deleteUser(@RequestParam("userId") String userId, @RequestParam("role") ERole role){
        this.userService.deleteUser(userId, role.toString());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("users")
    public UserDto  getUser(@RequestParam("userId") String userId, @RequestParam("role") ERole role){
        UserDto userDto = this.modelMapper.map(this.userService.getUser(userId, role.toString()), UserDto.class);
        return userDto;
    }

    @GetMapping("students/courses")
    public ResponseEntity<List<UserDto>> getAllStudentEnrollCourse(@RequestParam("courseId") String courseId){
        List<User> users = this.userService.getAllStudentWithInfoEnrolledCourse(courseId);
        List<UserDto> userDtos = this.utils.getListUserDto(users);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("students")
    public UserDto getStudent(@RequestParam("userId") String userId){
        User user = this.userService.getStudent(userId);
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @GetMapping("teachers")
    public UserDto getTeacher(@RequestParam("userId") String userId){
        User user = this.userService.getTeacher(userId);
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }

}
