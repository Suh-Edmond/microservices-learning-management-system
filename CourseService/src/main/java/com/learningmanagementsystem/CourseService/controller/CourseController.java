package com.learningmanagementsystem.CourseService.controller;

import com.learningmanagementsystem.CourseService.dto.CourseDto;
import com.learningmanagementsystem.CourseService.dto.payload.CoursePayload;
import com.learningmanagementsystem.CourseService.model.Course;
import com.learningmanagementsystem.CourseService.service.serviceImpl.CourseServiceImpl;
import com.learningmanagementsystem.CourseService.util.MessageResponse;
import com.learningmanagementsystem.CourseService.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/protected/")
public class CourseController {

    @Autowired
    CourseServiceImpl courseService;
    @Autowired
    ModelMapper modelMapper;
    Util util = new Util();

    @PostMapping("courses/create")
    public ResponseEntity<?> createCourse(@Valid @RequestBody CoursePayload coursePayload, @RequestParam("userId") String userId){
        Course course = this.util.getCourseFromCoursePayload(coursePayload);
        this.courseService.createCourse(course, userId);
        return  new ResponseEntity<>(new MessageResponse("success", "created course successfully", new Date()), HttpStatus.CREATED);
    }

    @GetMapping("course-all")
    public ResponseEntity<List<CourseDto>> getAllCourses(){
        List<Course> courses = this.courseService.getAllCourses();
        List<CourseDto> courseDtos = this.util.getCourseDtoList(courses);
        return new ResponseEntity<>(courseDtos, HttpStatus.OK);
    }

    @GetMapping("teachers/courses")
    public ResponseEntity<List<CourseDto>> getAllCoursesByTeacher(@RequestParam("userId") String userId){
        List<Course> courses = this.courseService.getAllCourseByTeacher(userId);
        List<CourseDto> courseDtos = this.util.getCourseDtoList(courses);
        return new ResponseEntity<>(courseDtos, HttpStatus.OK);
    }

    @GetMapping("teachers/{userId}/courses/{courseId}")
    public  ResponseEntity<CourseDto> getCourseByTeacher(@PathVariable("courseId") String courseId, @PathVariable("userId") String userId){
        Course course = this.courseService.getTeacherCourse(courseId, userId);
        CourseDto courseDto = this.util.getCourseDto(course);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @PutMapping("teachers/courses")
    public ResponseEntity<CourseDto> updatedCourse(@Valid @RequestBody CoursePayload coursePayload, @RequestParam("courseId") String courseId, @RequestParam("userId") String userId){
        Course edited =  this.util.getCourseFromCoursePayload(coursePayload);
        Course course = this.courseService.editCourse(edited, courseId, userId);
        CourseDto courseDto = this.util.getCourseDto(course);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @DeleteMapping("teachers/courses")
    public ResponseEntity<?> deleteCourse(@RequestParam("courseId") String courseId, @RequestParam("userId") String userId){
       this.courseService.deleteCourse(courseId, userId);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
