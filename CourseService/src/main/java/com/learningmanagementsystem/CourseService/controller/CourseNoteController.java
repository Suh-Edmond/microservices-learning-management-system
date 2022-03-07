package com.learningmanagementsystem.CourseService.controller;

import com.learningmanagementsystem.CourseService.dto.payload.CourseNotePayload;
import com.learningmanagementsystem.CourseService.service.serviceImpl.CourseNoteServiceImpl;
import com.learningmanagementsystem.CourseService.util.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("api/v1/protected/")
public class CourseNoteController {

    @Autowired
    CourseNoteServiceImpl courseNoteService;

    @PostMapping(path = "add-course-notes",  consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MessageResponse> createCourseNote(@RequestParam("courseId") String courseId, @Valid @ModelAttribute CourseNotePayload courseNotePayload){
        this.courseNoteService.CreateCourseNote(courseId, courseNotePayload);
        return new ResponseEntity<>(new MessageResponse("success", "Course note created successfully", new Date()), HttpStatus.CREATED);
    }
}
