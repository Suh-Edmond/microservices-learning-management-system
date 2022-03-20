package com.learningmanagementsystem.CourseService.proxy;

import com.learningmanagementsystem.CourseService.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="user-service")
public interface UserProxy {

    @GetMapping("teachers")
    public UserDto getTeacher(@RequestParam("userId") String userId);
    @GetMapping("students")
    public UserDto getStudent(@RequestParam("userId") String userId);
}
