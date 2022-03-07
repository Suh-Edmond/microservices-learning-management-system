package com.learningmanagementsystem.CourseService.controller;

import com.learningmanagementsystem.CourseService.dto.payload.CourseSyllabusPayload;
import com.learningmanagementsystem.CourseService.service.serviceImpl.CourseSyllabusServiceImpl;
import com.learningmanagementsystem.CourseService.util.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("api/v1/protected")
public class CourseSyllabusController {

    @Autowired
    private CourseSyllabusServiceImpl courseSyllabusService;

    @PostMapping(path = "add-course-syllabus",  consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MessageResponse> createCourseSyllabus(@RequestParam("courseId") String courseId, @Valid @ModelAttribute CourseSyllabusPayload courseSyllabusPayload){
        this.courseSyllabusService.createCourseSyllabus(courseId, courseSyllabusPayload);
        return new ResponseEntity<>(new MessageResponse("success", "Course syllabus created successfully", new Date()), HttpStatus.CREATED);
    }
}
