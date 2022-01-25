package com.learningmanagementsystem.UserService.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MethodArgumentNotValidExceptionResponse {

    private Date timeStamp;
    private String message;
    private HashMap<String, String> errors;
}
