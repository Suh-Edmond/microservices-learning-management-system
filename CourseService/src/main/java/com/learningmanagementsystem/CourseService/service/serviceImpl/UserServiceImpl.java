package com.learningmanagementsystem.CourseService.service.serviceImpl;

import com.learningmanagementsystem.CourseService.dto.UserDto;
import com.learningmanagementsystem.CourseService.exception.ResourceNotFoundException;
import com.learningmanagementsystem.CourseService.proxy.UserProxy;
import com.learningmanagementsystem.CourseService.repository.TeachCourseRepository;
import com.learningmanagementsystem.CourseService.service.UserService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserProxy userProxy;
    @Autowired
    TeachCourseRepository teachCourseRepository;

    @Override
    public List<String> getAllStudentsEnrollCourse(String courseId) {
        List<String> studentIds = this.teachCourseRepository.findAll().
                stream().filter(teachCourse -> teachCourse.getCourseId().equals(courseId)).map(teachCourse -> teachCourse.getUserId()).collect(Collectors.toList());
        return studentIds;
    }

    @Override
    public UserDto getStudentFromUserService(String userId) {
        UserDto userDto = new UserDto();
        try{
            userDto = this.userProxy.getStudent(userId);
        }catch(FeignException exception$NotFound){
            if(exception$NotFound.status() == 404){
                throw new ResourceNotFoundException("Resource not found with userId:"+ userId);
            }
        }
        return userDto;
    }

    @Override
    public UserDto getTeacherFromUserService(String userId) {
        UserDto userDto = new UserDto();
        try{
            userDto = this.userProxy.getTeacher(userId);
        }catch(FeignException exception$NotFound){
            if(exception$NotFound.status() == 404){
                throw new ResourceNotFoundException("Resource not found with userId:"+ userId);
            }
        }
        return userDto;
    }
}
