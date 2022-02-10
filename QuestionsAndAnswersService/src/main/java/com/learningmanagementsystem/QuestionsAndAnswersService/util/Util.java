package com.learningmanagementsystem.QuestionsAndAnswersService.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Util {

    private ModelMapper modelMapper;
    public Util(){
        this.modelMapper = new ModelMapper();
    }

    public String generateId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

//    public List<CourseDto> getCourseDtoList(List<Course> courseList){
//        List<CourseDto> courses = courseList.stream().map(course -> this.getCourseDto(course)).collect(Collectors.toList());
//        return courses;
//    }
//
//    public CourseDto getCourseDto(Course course){
//        CourseDto courseDto = this.modelMapper.map(course, CourseDto.class);
//        return courseDto;
//    }
//
//    public Course getCourseFromCoursePayload(CoursePayload coursePayload){
//        Course course = this.modelMapper.map(coursePayload, Course.class);
//        return course;
//    }


}
