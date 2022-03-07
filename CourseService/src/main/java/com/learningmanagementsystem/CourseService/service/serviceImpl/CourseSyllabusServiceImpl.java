package com.learningmanagementsystem.CourseService.service.serviceImpl;

import com.learningmanagementsystem.CourseService.dto.FileCategory;
import com.learningmanagementsystem.CourseService.dto.payload.CourseSyllabusPayload;
import com.learningmanagementsystem.CourseService.model.Course;
import com.learningmanagementsystem.CourseService.model.CourseSyllabus;
import com.learningmanagementsystem.CourseService.repository.CourseSyllabusRepository;
import com.learningmanagementsystem.CourseService.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

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
    public List<CourseSyllabus> getCourseSyllabuses(String courseId) {
        return null;
    }

    @Override
    public CourseSyllabus getCourseSyllabus(String courseId, String syllabusId) {
        return null;
    }
}
