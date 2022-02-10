package com.learningmanagementsystem.QuestionsAndAnswersService.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MessageResponse {

    private String message;
    private String details;
    private Date timeStamp;
}
