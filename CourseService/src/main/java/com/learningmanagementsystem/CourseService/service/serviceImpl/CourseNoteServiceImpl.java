package com.learningmanagementsystem.CourseService.service.serviceImpl;

import com.learningmanagementsystem.CourseService.dto.CourseNoteDto;
import com.learningmanagementsystem.CourseService.dto.FileCategory;
import com.learningmanagementsystem.CourseService.dto.UploadFileResponse;
import com.learningmanagementsystem.CourseService.dto.payload.CourseNotePayload;
import com.learningmanagementsystem.CourseService.dto.payload.CoursePayload;
import com.learningmanagementsystem.CourseService.exception.ResourceNotFoundException;
import com.learningmanagementsystem.CourseService.model.Course;
import com.learningmanagementsystem.CourseService.model.CourseNote;
import com.learningmanagementsystem.CourseService.repository.CourseNotesRepository;
import com.learningmanagementsystem.CourseService.service.CourseNotesService;
import com.learningmanagementsystem.CourseService.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<CourseNoteDto> getCourseNotes(String courseId) {
        List<CourseNoteDto> courseNoteDtoList = new ArrayList<>();
        Course course = this.courseService.getCourse(courseId);
        List<CourseNote> courseNotes = this.courseNotesRepository.findAll().
                stream().
                filter(courseNote ->  courseNote.getCourse().getId().equals(course.getId())).
                collect(Collectors.toList());
        courseNotes.stream().forEach(courseNote -> {
            CourseNoteDto courseNoteDto = this.getCourseNote(course.getId(), courseNote.getId());
            courseNoteDtoList.add(courseNoteDto);
        });
        return courseNoteDtoList;
    }

    @Override
    public CourseNoteDto getCourseNote(String courseId, String courseNoteId) {
        Course course = this.courseService.getCourse(courseId);
        Optional<CourseNote> courseNote = this.courseNotesRepository.findById(courseNoteId);
        courseNote.orElseThrow(() -> new ResourceNotFoundException("No Course Note Resource found with Id"+ courseNoteId));
        UploadFileResponse uploadFileResponse = this.fileStorageService.getCourseMaterial(course.getTitle(), FileCategory.NOTES, courseNote.get().getFile());
        CourseNoteDto courseNoteDto = this.util.getCourseNoteDto(courseNote.get());
        courseNoteDto.setNotes(uploadFileResponse);
        return courseNoteDto;
    }
}
