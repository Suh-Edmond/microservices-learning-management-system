package com.learningmanagementsystem.UserService.service.serviceImpl;

import com.learningmanagementsystem.UserService.exception.NotFoundException;
import com.learningmanagementsystem.UserService.proxy.CourseProxy;
import com.learningmanagementsystem.UserService.service.CourseService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseProxy courseProxy;

    @Override
    public List<String> getAllStudentsEnrolledToCourseFromCourseService(String courseId) {
        List<String> studentIds = new ArrayList<>();
        try{
            studentIds = this.courseProxy.getAllStudentIdsEnrollCourse(courseId);
        }catch(FeignException exception$NotFound){
            if(exception$NotFound.status() == 404){
                throw new NotFoundException("Resource not found with courseId:"+ courseId);
            }
        }
        return studentIds;
    }
}
