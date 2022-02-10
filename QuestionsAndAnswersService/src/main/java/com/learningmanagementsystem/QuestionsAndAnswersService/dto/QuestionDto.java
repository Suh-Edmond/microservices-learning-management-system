package com.learningmanagementsystem.QuestionsAndAnswersService.dto;

import com.learningmanagementsystem.QuestionsAndAnswersService.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {

    private String Id;
    private String courseId;
    private String studentId;
    private String image;
    private String subject;
    private String details;
    private List<Answer> answers;
}
