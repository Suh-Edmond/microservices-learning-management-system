package com.learningmanagementsystem.QuestionsAndAnswersService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AnswerDto {

    private String Id;
    private String response;
    private String details;
    private UploadFileResponse image;
    private String replierId;
    private String questionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
