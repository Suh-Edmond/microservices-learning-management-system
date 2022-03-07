package com.learningmanagementsystem.CourseService.controller;

import com.learningmanagementsystem.CourseService.dto.CourseSyllabusDto;
import com.learningmanagementsystem.CourseService.dto.FileCategory;
import com.learningmanagementsystem.CourseService.dto.UploadFileResponse;
import com.learningmanagementsystem.CourseService.dto.payload.CourseSyllabusPayload;
import com.learningmanagementsystem.CourseService.model.Course;
import com.learningmanagementsystem.CourseService.model.CourseSyllabus;
import com.learningmanagementsystem.CourseService.service.serviceImpl.CourseServiceImpl;
import com.learningmanagementsystem.CourseService.service.serviceImpl.CourseSyllabusServiceImpl;
import com.learningmanagementsystem.CourseService.service.serviceImpl.FileStorageServiceImpl;
import com.learningmanagementsystem.CourseService.util.MessageResponse;
import com.learningmanagementsystem.CourseService.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/protected")
public class CourseSyllabusController {

    @Autowired
    private CourseSyllabusServiceImpl courseSyllabusService;
    @Autowired
    private CourseServiceImpl courseService;
    @Autowired
    private FileStorageServiceImpl fileStorageService;
    private Util util = new Util();

    @PostMapping(path = "add-course-syllabus",  consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MessageResponse> createCourseSyllabus(@RequestParam("courseId") String courseId, @Valid @ModelAttribute CourseSyllabusPayload courseSyllabusPayload){
        this.courseSyllabusService.createCourseSyllabus(courseId, courseSyllabusPayload);
        return new ResponseEntity<>(new MessageResponse("success", "Course syllabus created successfully", new Date()), HttpStatus.CREATED);
    }

    @GetMapping("course-syllabus-all")
    public ResponseEntity<List<CourseSyllabusDto>> getCourseSyllabuses(@RequestParam("courseId") String courseId){
        List<CourseSyllabusDto> courseSyllabusDtoList = this.courseSyllabusService.getCourseSyllabuses(courseId);
        return new ResponseEntity<>(courseSyllabusDtoList, HttpStatus.OK);
    }

    @GetMapping("course-syllabuses")
    public ResponseEntity<CourseSyllabusDto> getCourseSyllabus(@RequestParam("courseId") String courseId, @RequestParam("syllabusId") String syllabusId){
        CourseSyllabusDto courseSyllabusDto = this.courseSyllabusService.getCourseSyllabus(courseId, syllabusId);
        return new ResponseEntity<>(courseSyllabusDto, HttpStatus.OK);
    }
}
