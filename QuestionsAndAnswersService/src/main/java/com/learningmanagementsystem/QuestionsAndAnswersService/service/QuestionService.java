package com.learningmanagementsystem.QuestionsAndAnswersService.service;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.ERole;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.QuestionDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.payload.QuestionPayload;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.FileCategory;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Question;

import java.util.List;

public interface QuestionService {

    public void createQuestion(QuestionPayload questionPayload, String courseId, String userId, ERole role, FileCategory fileCategory);
    public List<QuestionDto> getAllQuestions(String courseId);
    public Question getQuestion(String questionId);
    public List<QuestionDto> getCourseQuestions(String courseId);
    public QuestionDto updateQuestion(QuestionPayload questionPayload, String courseId, String questionId, String userId,  ERole role);
    public void deleteQuestion(String questionId, String userId, ERole role);
    public Question getUserQuestion(String questionId, String userId);
}
