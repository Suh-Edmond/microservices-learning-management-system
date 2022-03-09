package com.learningmanagementsystem.CourseService.service;

import com.learningmanagementsystem.CourseService.dto.FileCategory;
import com.learningmanagementsystem.CourseService.model.Course;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {

    public Course createCourse(Course course, String userId);
    public void uploadFile(String courseId, MultipartFile file, FileCategory fileCategory);
    public void addTeacherCourse(String userId, String courseId);
    public List<Course> getAllCourses();
    public List<Course> getAllCoursesByTeacher(String userId);
    public Course editCourse(Course course, String courseId, String userId);
    public void deleteCourse(String courseId, String userId);
    public Course getCourse(String courseId);
    public Course getTeacherCourse(String courseId, String userId);
    public  void enrollStudentToCourse(String courseId, String userId);
    public List<Course> getEnrolledCoursesByStudent(String userId);
    public Course getEnrolledCourseByStudent(String courseId, String userId);
    public List<Course> getAllCoursesByStatus(boolean status);
    public void approveCourse(String courseId);
    public void suspendCourse(String courseId);
    public void removeStudentFromCourse(String courseId, String teacherId, String studentId);
    public Course findCourseByTitle(String title);



}
