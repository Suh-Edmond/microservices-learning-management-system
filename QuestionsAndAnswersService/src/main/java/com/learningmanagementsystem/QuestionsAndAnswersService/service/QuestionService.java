package com.learningmanagementsystem.QuestionsAndAnswersService.service;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.ERole;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Question;

import java.util.List;

public interface QuestionService {

    public void createQuestion(Question question, String courseId, String userId,  ERole role);
    public List<Question> getAllQuestions();
    public Question getQuestion(String questionId);
    public List<Question> getCourseQuestions(String courseId);
    public Question updateQuestion(Question question,String questionId, String userId,  ERole role);
    public void deleteQuestion(String questionId, String userId, ERole role);
    public Question getUserQuestion(String questionId, String userId);
}
