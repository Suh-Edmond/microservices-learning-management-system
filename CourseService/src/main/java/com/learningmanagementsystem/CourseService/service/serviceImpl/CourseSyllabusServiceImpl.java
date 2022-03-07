package com.learningmanagementsystem.CourseService.service.serviceImpl;

import com.learningmanagementsystem.CourseService.dto.CourseSyllabusDto;
import com.learningmanagementsystem.CourseService.dto.FileCategory;
import com.learningmanagementsystem.CourseService.dto.UploadFileResponse;
import com.learningmanagementsystem.CourseService.dto.payload.CourseSyllabusPayload;
import com.learningmanagementsystem.CourseService.exception.ResourceNotFoundException;
import com.learningmanagementsystem.CourseService.model.Course;
import com.learningmanagementsystem.CourseService.model.CourseSyllabus;
import com.learningmanagementsystem.CourseService.repository.CourseSyllabusRepository;
import com.learningmanagementsystem.CourseService.service.CourseSyllabusService;
import com.learningmanagementsystem.CourseService.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseSyllabusServiceImpl implements CourseSyllabusService {
    @Autowired
    private CourseSyllabusRepository courseSyllabusRepository;
    @Autowired
    private CourseServiceImpl courseService;
    @Autowired
    private FileStorageServiceImpl fileStorageService;
    Util util = new Util();


    @Override
    public void createCourseSyllabus(String courseId, CourseSyllabusPayload courseSyllabusPayload) {
        CourseSyllabus courseSyllabus = new CourseSyllabus();
        Course course = this.courseService.getCourse(courseId);
        courseSyllabus.setId(this.util.generateId());
        courseSyllabus.setCourse(course);
        courseSyllabus.setFile(StringUtils.cleanPath(courseSyllabusPayload.getFile().getOriginalFilename()));
        this.courseSyllabusRepository.save(courseSyllabus);
        this.fileStorageService.uploadFile(course.getTitle(), FileCategory.SYLLABUSES, courseSyllabusPayload.getFile());
    }

    @Override
    public List<CourseSyllabusDto> getCourseSyllabuses(String courseId) {
        List<CourseSyllabusDto> courseSyllabusDtoList = new ArrayList<>();
        Course course = this.courseService.getCourse(courseId);
        List<CourseSyllabus> courseSyllabi = this.courseSyllabusRepository.findAll().
                stream().
                filter(courseSyllabus -> courseSyllabus.getCourse().getId().equals(course.getId())).
                collect(Collectors.toList());
        courseSyllabi.stream().forEach(courseSyllabus -> {
            CourseSyllabusDto courseSyllabusDto = this.getCourseSyllabus(course.getId(), courseSyllabus.getId());
            courseSyllabusDtoList.add(courseSyllabusDto);
        });
        return courseSyllabusDtoList;
    }

    @Override
    public CourseSyllabusDto getCourseSyllabus(String courseId, String syllabusId) {
        Course course = this.courseService.getCourse(courseId);
        Optional<CourseSyllabus> courseSyllabus = this.courseSyllabusRepository.findById(syllabusId);
        courseSyllabus.orElseThrow(() -> new ResourceNotFoundException("No Course Syllabus found with id"+ syllabusId));
        UploadFileResponse uploadFileResponse = this.fileStorageService.getCourseMaterial(course.getTitle(), FileCategory.SYLLABUSES, courseSyllabus.get().getFile());
        CourseSyllabusDto courseSyllabusDto = this.util.getCourseSyllabusDto(courseSyllabus.get());
        courseSyllabusDto.setSyllabus(uploadFileResponse);
        return courseSyllabusDto;


    }
}
