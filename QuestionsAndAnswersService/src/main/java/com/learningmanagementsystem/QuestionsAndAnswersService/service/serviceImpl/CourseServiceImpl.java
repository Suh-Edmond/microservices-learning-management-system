package com.learningmanagementsystem.QuestionsAndAnswersService.service.serviceImpl;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.CourseDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.UploadFileResponse;
import com.learningmanagementsystem.QuestionsAndAnswersService.exception.ResourceNotFoundException;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.FileCategory;
import com.learningmanagementsystem.QuestionsAndAnswersService.proxy.CourseProxy;
import com.learningmanagementsystem.QuestionsAndAnswersService.service.CourseService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseProxy courseProxy;

    @Override
    public CourseDto getCourseInfoFromCourseService(String courseId) {
        CourseDto courseDto = new CourseDto();
        try{
            courseDto = this.courseProxy.getCourse(courseId);
        }catch(FeignException exception$NotFound){
            if(exception$NotFound.status() == 404){
                throw new ResourceNotFoundException("Resource not found with courseId:"+ courseId);
            }
        }
        return courseDto;
    }

    @Override
    public CourseDto findCourseByTitle(String title) {
        CourseDto courseDto = new CourseDto();
        try{
            courseDto = this.courseProxy.findCourseByTitle(title);
        }catch(FeignException exception$NotFound){
            if(exception$NotFound.status() == 404){
                throw new ResourceNotFoundException("Resource not found with title:"+ title);
            }
        }
        return courseDto;
    }


}
