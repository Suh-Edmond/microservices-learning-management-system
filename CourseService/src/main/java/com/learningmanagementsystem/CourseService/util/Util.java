package com.learningmanagementsystem.CourseService.util;

import com.learningmanagementsystem.CourseService.dto.CourseDto;
import com.learningmanagementsystem.CourseService.dto.payload.CoursePayload;
import com.learningmanagementsystem.CourseService.model.Course;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Util {

    private ModelMapper modelMapper;
    public Util(){
        this.modelMapper = new ModelMapper();
    }

    public String generateId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public List<CourseDto> getCourseDtoList(List<Course> courseList){
        List<CourseDto> courses = courseList.stream().map(course -> this.getCourseDto(course)).collect(Collectors.toList());
        return courses;
    }

    public CourseDto getCourseDto(Course course){

        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setTitle(course.getTitle());
        courseDto.setLevel(course.getLevel());
        courseDto.setDescription(course.getDescription());
        courseDto.setPrice(course.getPrice());
        courseDto.setStatus(course.isStatus());
        return courseDto;
    }

    public Course getCourseFromCoursePayload(CoursePayload coursePayload){
        Course course = this.modelMapper.map(coursePayload, Course.class);
        return course;
    }


}
