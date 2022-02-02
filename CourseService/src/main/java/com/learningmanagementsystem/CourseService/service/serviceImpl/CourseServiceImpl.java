package com.learningmanagementsystem.CourseService.service.serviceImpl;

import com.learningmanagementsystem.CourseService.exception.ForbiddenException;
import com.learningmanagementsystem.CourseService.exception.ResourceAlreadyExistException;
import com.learningmanagementsystem.CourseService.exception.ResourceNotFoundException;
import com.learningmanagementsystem.CourseService.model.Course;
import com.learningmanagementsystem.CourseService.model.TeachCourse;
import com.learningmanagementsystem.CourseService.repository.CourseRepository;
import com.learningmanagementsystem.CourseService.repository.TeachCourseRepository;
import com.learningmanagementsystem.CourseService.service.CourseService;
import com.learningmanagementsystem.CourseService.util.Util;
import org.hibernate.exception.ConstraintViolationException;
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
    Util util = new Util();

    @Override
    public void createCourse(Course course, String userId) {
        //need to make a check to see if user id is valid
        course.setId(util.generateId());
        course.setStatus(true);
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
}
