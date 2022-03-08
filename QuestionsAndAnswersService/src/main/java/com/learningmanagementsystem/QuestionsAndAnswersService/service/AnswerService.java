package com.learningmanagementsystem.QuestionsAndAnswersService.service;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.ERole;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.payload.AnswerPayload;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Answer;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.FileCategory;

import java.util.List;

public interface AnswerService {

    public void createAnswer(AnswerPayload answerPayload, String questionId, String replierId, ERole role, FileCategory fileCategory);
    public List<Answer> getAllQuestionAnswers(String questionId);
    public Answer getQuestionAnswer(String answerId, String questionId);
    public Answer updateAnswer(AnswerPayload answerPayload, String questionId, String answerId, String replierId,  ERole role, FileCategory fileCategory);
    public List<Answer> getAllAnswersByReplier(String replierId, ERole role);
    public void deleteAnswer(String questionId, String answerId, String replierId,  ERole role);
    public Answer getReplierAnswer(String answerId, String replierId,  ERole role, String questionId);

}
