package com.learningmanagementsystem.UserService.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="course-service", url = "http://192.168.1.29:8100/api/v1/protected/")
public interface UserProxy {
    @GetMapping("students/courses")
    public List<String> getAllStudentIdsEnrollCourse(@RequestParam("courseId") String courseId);
}
