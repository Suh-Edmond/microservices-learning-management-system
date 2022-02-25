package com.learningmanagementsystem.QuestionsAndAnswersService.proxy;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.CourseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="course-service", url = "http://10.0.0.16:8100/api/v1/protected/")
public interface CourseProxy {

    @GetMapping("courses/{courseId}")
    public CourseDto getCourse(@PathVariable("courseId") String courseId);
}
