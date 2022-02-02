package com.learningmanagementsystem.CourseService.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseNoteDto {

    private String Id;
    private String topic;
    private String courseId;
}
