package com.learningmanagementsystem.QuestionsAndAnswersService.proxy;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.CourseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="course-service")
public interface CourseProxy {

    @GetMapping("courses/{courseId}")
    public CourseDto getCourse(@PathVariable("courseId") String courseId);

    @GetMapping("courses-search")
    public CourseDto findCourseByTitle(@RequestParam("title") String title);
}
