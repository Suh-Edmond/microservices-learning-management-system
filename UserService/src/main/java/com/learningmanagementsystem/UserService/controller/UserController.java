package com.learningmanagementsystem.UserService.controller;


import com.learningmanagementsystem.UserService.Utils.Utils;
import com.learningmanagementsystem.UserService.dto.UserDto;
import com.learningmanagementsystem.UserService.model.User;
import com.learningmanagementsystem.UserService.service.serviceImpl.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/protected/")
@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    ModelMapper modelMapper;
    private Utils utils = new Utils();

    @GetMapping("teachers")
    public ResponseEntity<List<UserDto>> getAllTeachers(){
        List<User> users = this.userService.getAllTeachers();
        List<UserDto> userDtos = this.utils.getListUserDto(users);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);

    }

    @GetMapping("students")
    public ResponseEntity<List<UserDto>> getStudents() {
        List<User> users = this.userService.getAllStudents();
        List<UserDto> userDtos = this.utils.getListUserDto(users);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @DeleteMapping("users")
    public ResponseEntity<?>  deleteUser(@RequestParam("userId") String userId){
        this.userService.deleteUser(userId);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @GetMapping("users")
    public ResponseEntity<UserDto>  getUser(@RequestParam("userId") String userId){
        UserDto userDto = this.modelMapper.map(this.userService.getUser(userId), UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

//    @GetMapping("students/courses")
//    public List<UserDto> getAllStudentEnrollCourse(@RequestParam("courseId") String courseId){
//        List<UserDto> userDtos = this.userService.getAllStudentEnrolledCourse(couse)
//    }


}
