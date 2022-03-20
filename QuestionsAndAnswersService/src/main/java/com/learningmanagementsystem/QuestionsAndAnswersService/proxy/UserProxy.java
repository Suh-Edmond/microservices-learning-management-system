package com.learningmanagementsystem.QuestionsAndAnswersService.proxy;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.ERole;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="course-service", url = "http://192.168.43.246:8000/api/v1/protected/")
public interface UserProxy {
    @GetMapping("users")
    public UserDto getUser(@RequestParam("userId") String userId, @RequestParam("role") ERole role);
}
