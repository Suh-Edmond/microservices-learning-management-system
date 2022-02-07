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

    @GetMapping("courses/enroll-student-to-course")
    public ResponseEntity<MessageResponse> enrollStudentToCourse(@RequestParam("courseId") String courseId, @RequestParam("userId") String userId) {
        this.courseService.enrollStudentToCourse(courseId, userId);
        return new ResponseEntity<>(new MessageResponse("success", "Student enrolled to course successfully",new Date()), HttpStatus.CREATED);
    }

    @GetMapping("courses/get-enrolled-courses-by-student")
    public ResponseEntity<List<CourseDto>> getAllEnrolledCoursesByStudent(@RequestParam("userId") String userId) {
        List<Course> courses = this.courseService.getEnrolledCoursesByStudent(userId);
        List<CourseDto> courseDtos = this.util.getCourseDtoList(courses);
        return new ResponseEntity<>(courseDtos, HttpStatus.OK);
    }

    @GetMapping("courses/get-enrolled-course")
    public ResponseEntity<CourseDto> getAllEnrolledCourseByStudent(@RequestParam("courseId") String courseId, @RequestParam("userId") String userId){
        Course course = this.courseService.getEnrolledCourseByStudent(courseId, userId);
        CourseDto courseDto = this.util.getCourseDto(course);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @GetMapping("courses")
    public ResponseEntity<List<CourseDto>> getAllActiveCourses(@RequestParam("status") boolean status){
        List<Course> courses = this.courseService.getAllByCourses(status);
        List<CourseDto> courseDtos = this.util.getCourseDtoList(courses);
        return new ResponseEntity<>(courseDtos, HttpStatus.OK);
    }

    @PutMapping("courses/approve-course")
    public ResponseEntity<?> approvedCourse(@RequestParam("courseId") String courseId){
        this.courseService.approveCourse(courseId);
        return new ResponseEntity<>(new MessageResponse("success", "Course approved successfully", new Date()), HttpStatus.OK);
    }

    @PutMapping("courses/suspend-course")
    public ResponseEntity<?> suspendCourse(@RequestParam("courseId") String courseId){
        this.courseService.suspendCourse(courseId);
        return new ResponseEntity<>(new MessageResponse("success", "Course suspended successfully", new Date()), HttpStatus.OK);
    }

    @DeleteMapping("courses/remove-student-from-course")
    public ResponseEntity<MessageResponse> removeStudentEnrolledToCourse(@RequestParam("courseId") String courseId, @RequestParam("userId") String userId){
        this.courseService.removeStudentFromCourse(courseId, userId);
        return new ResponseEntity<>(new MessageResponse("success", "Student has been remove from course", new Date()), HttpStatus.OK);
    }

    @GetMapping("students/courses")
    public ResponseEntity<?> getAllStudentsEnrollCourse(@RequestParam("courseId") String courseId){
        List<String> studentIds = this.courseService.getAllStudentsEnrollCourse(courseId);
        return new ResponseEntity<>(studentIds, HttpStatus.OK);
    }
}
