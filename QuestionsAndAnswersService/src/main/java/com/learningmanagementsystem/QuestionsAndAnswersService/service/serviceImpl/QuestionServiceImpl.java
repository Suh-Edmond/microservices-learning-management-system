package com.learningmanagementsystem.QuestionsAndAnswersService.service.serviceImpl;

import com.learningmanagementsystem.QuestionsAndAnswersService.exception.ForbiddenException;
import com.learningmanagementsystem.QuestionsAndAnswersService.exception.ResourceNotFoundException;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Question;
import com.learningmanagementsystem.QuestionsAndAnswersService.repository.QuestionRepository;
import com.learningmanagementsystem.QuestionsAndAnswersService.service.QuestionService;
import com.learningmanagementsystem.QuestionsAndAnswersService.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private Util util = new Util();
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void createQuestion(Question question, String courseId, String userId) {
        question.setId(this.util.generateId());
        question.setCourseId(courseId);
        question.setUserId(userId);
        this.questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        List<Question> questions = this.questionRepository.findAll();
        return questions;
    }

    @Override
    public Question getQuestion(String questionId) {
        Optional<Question> question = this.questionRepository.findById(questionId);
        question.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        return question.get();
    }

    @Override
    public List<Question> getCourseQuestions(String courseId) {
        List<Question> questions = this.questionRepository.findAll().
                stream().
                filter(question -> question.getCourseId().equals(courseId)).
                collect(Collectors.toList());
        return questions;
    }

    @Override
    public Question updateQuestion(Question question, String questionId, String userId) {
        Question question1 = this.getQuestion(questionId);
        Question question2 = this.getUserQuestion(question1.getId(), userId);
        question2.setTopic(question.getTopic());
        question2.setDetails(question.getDetails());
        question2.setImage(question.getImage());
        Question updatedQuestion = this.questionRepository.saveAndFlush(question1);
        return updatedQuestion;
    }

    @Override
    public void deleteQuestion(String questionId, String userId) {
        Question question1 = this.getQuestion(questionId);
        Question question2 = this.getUserQuestion(question1.getId(), userId);
        this.questionRepository.delete(question2);
    }

    @Override
    public Question getUserQuestion(String questionId, String userId) {
        Optional<Question> questionOptional = this.questionRepository.findAll().
                stream().
                filter(question -> question.getId().equals(questionId) && question.getUserId().equals(userId)).
                findFirst();
        questionOptional.orElseThrow(() -> new ForbiddenException("Access denied! You can not modify a question created by another user"));
        return questionOptional.get();
    }
}
