package com.learningmanagementsystem.QuestionsAndAnswersService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends RuntimeException {

	public AccessDeniedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	
}
