package com.learningmanagementsystem.CourseService.controller;

import com.learningmanagementsystem.CourseService.dto.CourseDto;
import com.learningmanagementsystem.CourseService.dto.FileCategory;
import com.learningmanagementsystem.CourseService.dto.UploadFileResponse;
import com.learningmanagementsystem.CourseService.dto.payload.CoursePayload;
import com.learningmanagementsystem.CourseService.model.Course;
import com.learningmanagementsystem.CourseService.service.serviceImpl.CourseServiceImpl;
import com.learningmanagementsystem.CourseService.service.serviceImpl.UserServiceImpl;
import com.learningmanagementsystem.CourseService.util.MessageResponse;
import com.learningmanagementsystem.CourseService.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/COURSE-SERVICE/api/v1/protected/")
public class CourseController {

    @Autowired
    private CourseServiceImpl courseService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    private ModelMapper modelMapper;
    private Util util;
    public  CourseController(){
        this.util = new Util();
    }

    @PostMapping("courses/create")
    public ResponseEntity<?> createCourse(@Valid @RequestBody CoursePayload coursePayload,
                                          @RequestParam("userId") String userId){
        Course course = this.util.getCourseFromCoursePayload(coursePayload);
        Course created = this.courseService.createCourse(course, userId);
        return  new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PatchMapping("courses/upload-course-image")
    public ResponseEntity<MessageResponse> uploadCourseImage(@RequestParam("courseId") String courseId,
                                                             @RequestPart("file")MultipartFile file,
                                                             @RequestParam("fileCategory") FileCategory fileCategory){
        this.courseService.uploadFile(courseId, file, fileCategory);
        return new ResponseEntity<>(new MessageResponse("success", "Uploaded file successfully", new Date()), HttpStatus.OK);
    }


    @GetMapping("course-all")
    public ResponseEntity<List<CourseDto>> getAllCourses(){
        List<CourseDto> courseDtoList = new ArrayList<>();
        List<Course> courses = this.courseService.getAllCourses();
        courses.stream().forEach(course ->  {
            CourseDto courseDto = this.courseService.generateCourseDto(course);
            courseDtoList.add(courseDto);
        });
        return new ResponseEntity<>(courseDtoList, HttpStatus.OK);
    }

    @GetMapping("courses/{courseId}")
    public CourseDto getCourse(@PathVariable("courseId") String courseId){
        Course course = this.courseService.getCourse(courseId);
        CourseDto courseDto = this.courseService.generateCourseDto(course);
        return courseDto;
    }

    @GetMapping("teachers/courses")
    public ResponseEntity<List<CourseDto>> getAllCoursesByTeacher(@RequestParam("userId") String userId){
        List<CourseDto> courseDtoList = new ArrayList<>();
        List<Course> courses = this.courseService.getAllCoursesByTeacher(userId);
        courses.stream().forEach(course -> {
            CourseDto courseDto = this.courseService.generateCourseDto(course);
            courseDtoList.add(courseDto);
        });
        return new ResponseEntity<>(courseDtoList, HttpStatus.OK);
    }

    @GetMapping("teachers/{userId}/courses/{courseId}/role")
    public  ResponseEntity<CourseDto> getCourseByTeacher(@PathVariable("courseId") String courseId, @PathVariable("userId") String userId){
        Course course = this.courseService.getTeacherCourse(courseId, userId);
        CourseDto courseDto = this.courseService.generateCourseDto(course);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @PutMapping("teachers/courses")
    public ResponseEntity<CourseDto> updatedCourse(@Valid @RequestBody CoursePayload coursePayload,
                                                   @RequestParam("courseId") String courseId,
                                                   @RequestParam("userId") String userId){
        Course edited =  this.util.getCourseFromCoursePayload(coursePayload);
        Course course = this.courseService.editCourse(edited, courseId, userId);
        CourseDto courseDto = this.courseService.generateCourseDto(course);
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
        List<CourseDto> courseDtoList = new ArrayList<>();
        List<Course> courses = this.courseService.getEnrolledCoursesByStudent(userId);
        courses.stream().forEach(course -> {
            CourseDto courseDto = this.courseService.generateCourseDto(course);
            courseDtoList.add(courseDto);
        });
        return new ResponseEntity<>(courseDtoList, HttpStatus.OK);
    }

    @GetMapping("courses/get-enrolled-course")
    public ResponseEntity<CourseDto> getAllEnrolledCourseByStudent(@RequestParam("courseId") String courseId, @RequestParam("userId") String userId){
        Course course = this.courseService.getEnrolledCourseByStudent(courseId, userId);
        CourseDto courseDto = this.courseService.generateCourseDto(course);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @GetMapping("courses")
    public ResponseEntity<List<CourseDto>> getAllActiveCourses(@RequestParam("status") boolean status){
        List<CourseDto> courseDtoList = new ArrayList<>();
        List<Course> courses = this.courseService.getAllCoursesByStatus(status);
        courses.stream().forEach(course -> {
            CourseDto courseDto = this.courseService.generateCourseDto(course);
            courseDtoList.add(courseDto);
        });
        return new ResponseEntity<>(courseDtoList, HttpStatus.OK);
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
    public ResponseEntity<MessageResponse> removeStudentEnrolledToCourse(@RequestParam("courseId") String courseId,
                                                                         @RequestParam("teacherId") String teacherId,
                                                                         @RequestParam("studentId") String studentId){
        this.courseService.removeStudentFromCourse(courseId, teacherId, studentId);
        return new ResponseEntity<>(new MessageResponse("success", "Student has been remove from course", new Date()), HttpStatus.OK);
    }

    @GetMapping("students/courses")
    public List<String> getAllStudentIdsEnrollCourse(@RequestParam("courseId") String courseId){
        List<String> studentIds = this.userService.getAllStudentsEnrollCourse(courseId);
        return studentIds;
    }


    @GetMapping("courses-search")
    public CourseDto findCourseByTitle(@RequestParam("title") String title){
        Course course = this.courseService.findCourseByTitle(title);
        CourseDto courseDto = this.util.getCourseDto(course);
        return  courseDto;
    }
}
