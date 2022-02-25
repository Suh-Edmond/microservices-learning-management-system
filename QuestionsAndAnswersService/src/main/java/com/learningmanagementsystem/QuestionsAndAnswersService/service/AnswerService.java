package com.learningmanagementsystem.QuestionsAndAnswersService.service;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.ERole;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Answer;

import java.util.List;

public interface AnswerService {

    public void createAnswer(Answer answer, String questionId, String replierId, ERole role);
    public List<Answer> getAllQuestionAnswers(String questionId);
    public Answer getQuestionAnswer(String answerId, String questionId);
    public Answer updateAnswer(Answer answer, String questionId, String answerId, String replierId,  ERole role);
    public List<Answer> getAllAnswersByReplier(String replierId, ERole role);
    public void deleteAnswer(String questionId, String answerId, String replierId,  ERole role);
    public Answer getReplierAnswer(String answerId, String replierId,  ERole role, String questionId);

}
