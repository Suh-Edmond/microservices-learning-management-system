package com.learningmanagementsystem.CourseService.service.serviceImpl;

import com.learningmanagementsystem.CourseService.exception.ForbiddenException;
import com.learningmanagementsystem.CourseService.exception.ResourceAlreadyExistException;
import com.learningmanagementsystem.CourseService.exception.ResourceNotFoundException;
import com.learningmanagementsystem.CourseService.model.Course;
import com.learningmanagementsystem.CourseService.model.EnrollCourse;
import com.learningmanagementsystem.CourseService.model.TeachCourse;
import com.learningmanagementsystem.CourseService.repository.CourseRepository;
import com.learningmanagementsystem.CourseService.repository.EnrollCourseRepository;
import com.learningmanagementsystem.CourseService.repository.TeachCourseRepository;
import com.learningmanagementsystem.CourseService.service.CourseService;
import com.learningmanagementsystem.CourseService.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TeachCourseRepository teachCourseRepository;
    @Autowired
    EnrollCourseRepository  enrollCourseRepository;
    Util util = new Util();

    @Override
    public void createCourse(Course course, String userId) {
        //need to make a check to see if user id is valid
        course.setId(util.generateId());
        course.setStatus(false);
        Course createdCourse;
        try {
            createdCourse = courseRepository.saveAndFlush(course);
        } catch (DataIntegrityViolationException exception) {
            throw new ResourceAlreadyExistException("Course Title already exist");
        }
        TeachCourse teachCourse = new TeachCourse();
        teachCourse.setId(util.generateId());
        teachCourse.setCourseId(createdCourse.getId());
        teachCourse.setUserId(userId);
        teachCourseRepository.save(teachCourse);
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses;
    }

    @Override
    public List<Course> getAllCourseByTeacher(String userId) {
        List<Course> courseList = new ArrayList<>();
        List<TeachCourse> teachCourses = teachCourseRepository.
                findAll().
                stream().
                filter(teachCourse -> teachCourse.getUserId().equals(userId)).
                collect(Collectors.toList());
        if(!teachCourses.isEmpty()){
            teachCourses.forEach(teachCourse -> {
                Course course = this.getCourse(teachCourse.getCourseId());
                courseList.add(course);
            });
        }
        return courseList;
    }

    @Override
    public Course editCourse(Course course, String courseId, String userId) {
        Course edited = this.getTeacherCourse(courseId, userId);
        edited.setTitle(course.getTitle());
        edited.setDescription(course.getDescription());
        edited.setLevel(course.getLevel());
        edited.setPrice(course.getPrice());
        Course updatedCoursed = courseRepository.saveAndFlush(edited);
        return updatedCoursed;
    }

    @Override
    public void deleteCourse(String courseId, String userId) {
        Course course = this.getTeacherCourse(courseId, userId);
        TeachCourse teachCourse = teachCourseRepository.
                findAll().
                stream().
                filter(teachCourse1 -> teachCourse1.getCourseId().equals(course.getId())).
                findFirst().get();
        courseRepository.delete(course);
        teachCourseRepository.delete(teachCourse);
    }

    @Override
    public Course getCourse(String courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        course.orElseThrow(() -> new ResourceNotFoundException("No course resource found with id:"+ courseId));
        return course.get();
    }

    @Override
    public Course getTeacherCourse(String courseId, String userId) {
        Course course = this.getCourse(courseId);
        Optional<TeachCourse> teachCourse = teachCourseRepository.findAll().
                stream().
                filter(teachCourse1 -> teachCourse1.getCourseId().equals(course.getId()) && teachCourse1.getUserId().equals(userId)).
                findFirst();
        teachCourse.orElseThrow(() -> new ForbiddenException("You don't have permission to access this resource"));
        return course;
    }

    @Override
    public void enrollStudentToCourse(String courseId, String userId) {
        Course course = this.getCourse(courseId);
        if(!course.isStatus()){
            throw  new ForbiddenException("This Course not is currently not available for enrollment");
        }
        EnrollCourse enrollCourse = new EnrollCourse();
        enrollCourse.setId(this.util.generateId());
        enrollCourse.setCourseId(course.getId());
        enrollCourse.setUserId(userId);
        enrollCourseRepository.save(enrollCourse);
    }

    @Override
    public List<Course> getEnrolledCoursesByStudent(String userId) {
        List<Course> courseList = new ArrayList<>();
        List<EnrollCourse> enrolledCourse = this.enrollCourseRepository.
                findAll().
                stream().
                filter(enrollCourse -> enrollCourse.getUserId().equals(userId)).
                collect(Collectors.toList());
        if(!enrolledCourse.isEmpty()) {
            enrolledCourse.forEach(course -> {
                Course course1 = this.getCourse(course.getCourseId());
                courseList.add(course1);
            });
        }
        return courseList;
    }

    @Override
    public Course getEnrolledCourseByStudent(String courseId, String userId) {
        List<Course> courses = this.getEnrolledCoursesByStudent(userId);
        Optional<Course> course = courses.stream().filter(course1 -> course1.getId().equals(courseId)).findFirst();
        return course.get();
    }

    @Override
    public List<Course> getAllByCourses(boolean status) {
        List<Course> courses = this.courseRepository.
                findAll().
                stream().
                filter(course -> course.isStatus() == status).
                collect(Collectors.toList());
        return courses;
    }

    @Override
    public void approveCourse(String courseId) {
        Course course = this.getCourse(courseId);
        course.setStatus(true);
    }

    @Override
    public void suspendCourse(String courseId) {
        Course course = this.getCourse(courseId);
        course.setStatus(false);
    }

    @Override
    public void removeStudentFromCourse(String courseId, String userId) {
        Optional<EnrollCourse> enrollCourse = this.enrollCourseRepository.findAll().
                stream().filter(enrollCourse1 -> enrollCourse1.getCourseId().equals(courseId) && enrollCourse1.getUserId().equals(userId)).
                findFirst();
        this.enrollCourseRepository.delete(enrollCourse.get());
    }

    @Override
    public List<String> getAllStudentsEnrollCourse(String courseId) {
        List<String> studentIds = this.teachCourseRepository.findAll().
                stream().filter(teachCourse -> teachCourse.getCourseId().equals(courseId)).map(teachCourse -> teachCourse.getUserId()).collect(Collectors.toList());
        return studentIds;
    }

}
