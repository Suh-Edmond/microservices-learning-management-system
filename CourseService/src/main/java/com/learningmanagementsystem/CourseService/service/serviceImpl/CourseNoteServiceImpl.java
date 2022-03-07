package com.learningmanagementsystem.CourseService.service.serviceImpl;

import com.learningmanagementsystem.CourseService.dto.FileCategory;
import com.learningmanagementsystem.CourseService.dto.payload.CourseNotePayload;
import com.learningmanagementsystem.CourseService.dto.payload.CoursePayload;
import com.learningmanagementsystem.CourseService.model.Course;
import com.learningmanagementsystem.CourseService.model.CourseNote;
import com.learningmanagementsystem.CourseService.repository.CourseNotesRepository;
import com.learningmanagementsystem.CourseService.service.CourseNotesService;
import com.learningmanagementsystem.CourseService.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CourseNoteServiceImpl implements CourseNotesService {

    @Autowired
    private CourseNotesRepository courseNotesRepository;
    @Autowired
    private CourseServiceImpl courseService;
    @Autowired
    private FileStorageServiceImpl fileStorageService;
    Util util = new Util();

    @Override
    public void CreateCourseNote(String courseId, CourseNotePayload courseNotePayload) {
        CourseNote courseNote = new CourseNote();
        Course course = this.courseService.getCourse(courseId);
        courseNote.setId(this.util.generateId());
        courseNote.setCourse(course);
        courseNote.setTopic(courseNotePayload.getTopic());
        courseNote.setFile(StringUtils.cleanPath(courseNotePayload.getFile().getOriginalFilename()));
        this.courseNotesRepository.save(courseNote);
        this.fileStorageService.uploadFile(course.getTitle(), FileCategory.NOTES, courseNotePayload.getFile());
    }

    @Override
    public List<CourseNote> getCourseNotes(String courseId) {
        return null;
    }

    @Override
    public CourseNote getCourseNote(String courseId, String courseNoteId) {
        return null;
    }
}
