package com.learningmanagementsystem.QuestionsAndAnswersService.dto;

import com.learningmanagementsystem.QuestionsAndAnswersService.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {

    private String Id;
    private String courseId;
    private String userId;
    private UploadFileResponse image;
    private String topic;
    private String details;
    private List<Answer> answers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
