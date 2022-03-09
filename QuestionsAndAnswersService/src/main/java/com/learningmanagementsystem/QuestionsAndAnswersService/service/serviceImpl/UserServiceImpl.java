package com.learningmanagementsystem.QuestionsAndAnswersService.service.serviceImpl;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.ERole;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.UserDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.exception.ResourceNotFoundException;
import com.learningmanagementsystem.QuestionsAndAnswersService.proxy.UserProxy;
import com.learningmanagementsystem.QuestionsAndAnswersService.service.UserService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserProxy userProxy;


    @Override
    public UserDto getUserFromUserService(String userId, ERole role) {
        UserDto userDto = new UserDto();
        try{
            userDto = this.userProxy.getUser(userId, role);
        }catch(FeignException exception$NotFound){
            if(exception$NotFound.status() == 404){
                throw new ResourceNotFoundException("Resource not found with userId:"+ userId);
            }
        }
        return userDto;
    }
}
