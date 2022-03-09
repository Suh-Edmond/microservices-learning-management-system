package com.learningmanagementsystem.CourseService.service.serviceImpl;

import com.learningmanagementsystem.CourseService.dto.CourseDto;
import com.learningmanagementsystem.CourseService.dto.FileCategory;
import com.learningmanagementsystem.CourseService.dto.UploadFileResponse;
import com.learningmanagementsystem.CourseService.dto.UserDto;
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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private FileStorageServiceImpl fileStorageService;
    Util util = new Util();

    @Override
    public Course createCourse(Course course, String userId) {
        course.setId(util.generateId());
        course.setStatus(false);
        Course createdCourse;
        try {
            createdCourse = courseRepository.saveAndFlush(course);
        } catch (DataIntegrityViolationException exception) {
            throw new ResourceAlreadyExistException("Course Title already exist");
        }
        this.addTeacherCourse(userId, createdCourse.getId());

        return createdCourse;
    }

    @Override
    public void uploadFile(String courseId, MultipartFile file, FileCategory fileCategory) {
        Optional<Course> course = this.courseRepository.findById(courseId);
        course.orElseThrow(()-> new ResourceNotFoundException("Resource not found with courseId:"+ courseId));
        course.get().setCourseImage(StringUtils.cleanPath(file.getOriginalFilename()));
        this.fileStorageService.uploadFile(course.get().getTitle(), fileCategory, file);
        this.courseRepository.save(course.get());
    }

    @Override
    public void addTeacherCourse(String userId, String courseId) {
        TeachCourse teachCourse = new TeachCourse();
        teachCourse.setId(util.generateId());
        teachCourse.setCourseId(courseId);
        UserDto userDto = this.userService.getTeacherFromUserService(userId);
        teachCourse.setUserId(userDto.getId());
        teachCourseRepository.save(teachCourse);
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses;
    }

    @Override
    public List<Course> getAllCoursesByTeacher(String userId) {
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
        UserDto userDto = this.userService.getTeacherFromUserService(userId);
        Course course = this.getCourse(courseId);
        Optional<TeachCourse> teachCourse = teachCourseRepository.findAll().
                stream().
                filter(teachCourse1 -> teachCourse1.getCourseId().equals(course.getId()) && teachCourse1.getUserId().equals(userDto.getId())).
                findFirst();
        teachCourse.orElseThrow(() -> new ForbiddenException("You don't have permission to access this resource"));
        return course;
    }

    @Override
    public void enrollStudentToCourse(String courseId, String userId) {
        UserDto userDto = this.userService.getStudentFromUserService(userId);
        Course course = this.getCourse(courseId);
        if(!course.isStatus()){
            throw  new ForbiddenException("This Course not is currently not available for enrollment");
        }
        EnrollCourse enrollCourse = new EnrollCourse();
        enrollCourse.setId(this.util.generateId());
        enrollCourse.setCourseId(course.getId());
        enrollCourse.setUserId(userDto.getId());
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
        UserDto userDto = this.userService.getStudentFromUserService(userId);
        List<Course> courses = this.getEnrolledCoursesByStudent(userDto.getId());
        Optional<Course> course = courses.stream().filter(course1 -> course1.getId().equals(courseId)).findFirst();
        return course.get();
    }

    @Override
    public List<Course> getAllCoursesByStatus(boolean status) {
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
        this.courseRepository.save(course);
    }

    @Override
    public void suspendCourse(String courseId) {
        Course course = this.getCourse(courseId);
        course.setStatus(false);
        this.courseRepository.save(course);
    }

    @Override
    public void removeStudentFromCourse(String courseId, String teacherId, String studentId) {
        Course course = this.getTeacherCourse(courseId, teacherId);
        UserDto enrolledStudent = this.userService.getStudentFromUserService(studentId);
        Optional<EnrollCourse> enrollCourse = this.enrollCourseRepository.findAll().
                stream().filter(enrollCourse1 -> enrollCourse1.getCourseId().equals(course.getId()) && enrollCourse1.getUserId().equals(enrolledStudent.getId())).
                findFirst();
        enrollCourse.orElseThrow(() -> new ResourceNotFoundException("Student is not enrolled to course"));
        this.enrollCourseRepository.delete(enrollCourse.get());
    }

    @Override
    public Course findCourseByTitle(String title) {
        Optional<Course> course = this.courseRepository.findByTitle(title);
        course.orElseThrow(() -> new ResourceNotFoundException("Resource not found with title "+title));
        return course.get();
    }



    public CourseDto generateCourseDto(Course course){
        UploadFileResponse uploadFileResponse = this.fileStorageService.getCourseMaterial(course.getTitle(), FileCategory.IMAGES, course.getCourseImage());;
        CourseDto courseDto = this.util.getCourseDto(course);
        courseDto.setCourseImage(uploadFileResponse);
        return  courseDto;
    }


}
