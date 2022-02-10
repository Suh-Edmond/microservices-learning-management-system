package com.learningmanagementsystem.QuestionsAndAnswersService.dto;

import com.learningmanagementsystem.QuestionsAndAnswersService.model.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AnswerDto {

    private String Id;
    private String response;
    private String details;
    private String image;
    private String replierId;
    private Question question;
}
