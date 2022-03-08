package com.learningmanagementsystem.CourseService.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseNoteDto {

    private String Id;
    private String topic;
    private String courseId;
    private UploadFileResponse notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
