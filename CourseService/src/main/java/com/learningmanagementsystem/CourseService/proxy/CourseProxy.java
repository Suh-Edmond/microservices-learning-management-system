package com.learningmanagementsystem.CourseService.proxy;

import com.learningmanagementsystem.CourseService.dto.UserDto;
import com.learningmanagementsystem.CourseService.model.ERole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="user-service", url = "http://192.168.1.29:8000/api/v1/protected/")
public interface CourseProxy {

    @GetMapping("users")
    public UserDto getUser(@RequestParam("userId") String userId, @RequestParam("role") ERole role);
}
