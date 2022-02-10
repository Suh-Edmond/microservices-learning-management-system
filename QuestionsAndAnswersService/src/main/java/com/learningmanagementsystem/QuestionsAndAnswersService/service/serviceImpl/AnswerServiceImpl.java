package com.learningmanagementsystem.QuestionsAndAnswersService.service.serviceImpl;

import com.learningmanagementsystem.QuestionsAndAnswersService.exception.ResourceNotFoundException;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Answer;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Question;
import com.learningmanagementsystem.QuestionsAndAnswersService.repository.AnswerRepository;
import com.learningmanagementsystem.QuestionsAndAnswersService.service.AnswerService;
import com.learningmanagementsystem.QuestionsAndAnswersService.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AnswerServiceImpl implements AnswerService {

    private Util util = new Util();
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionServiceImpl questionService;

    @Override
    public void createAnswer(Answer answer, String questionId) {
        answer.setId(this.util.generateId());
        Question question = this.questionService.getQuestion(questionId);
        answer.setQuestion(question);
        this.answerRepository.save(answer);
    }

    @Override
    public List<Answer> getAllQuestionAnswers(String questionId) {
        List<Answer> answers = this.answerRepository.findAll().
                stream().
                filter(answer -> answer.getQuestion().getId().equals(questionId)).
                collect(Collectors.toList());
        return answers;
    }

    @Override
    public Answer getQuestionAnswer(String answerId, String questionId) {
        Optional<Answer> answerOptional = this.answerRepository.findAll().
                stream().
                filter(answer -> answer.getId().equals(answerId) &&
                        answer.getQuestion().getId().equals(questionId)).
                findFirst();
        answerOptional.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        return answerOptional.get();
    }

    @Override
    public Answer updateAnswer(Answer answer, String questionId, String answerId, String replierId) {
        Answer answer1 = this.getReplierAnswer(answerId, replierId, questionId);
        answer1.setDetails(answer.getDetails());
        answer1.setResponse(answer.getResponse());
        answer1.setImage(answer.getImage());
        this.answerRepository.save(answer1);
        return answer1;
    }

    @Override
    public List<Answer> getAllAnswersByReplier(String replierId) {
        List<Answer> answerList = this.answerRepository.findAll().
                stream().
                filter(answer -> answer.getReplierId().equals(replierId)).
                collect(Collectors.toList());
        return answerList;
    }

    @Override
    public void deleteAnswer(String questionId, String answerId, String replierId) {
        Answer answer = this.getReplierAnswer(answerId, replierId, questionId);
        this.answerRepository.delete(answer);
    }

    @Override
    public Answer getReplierAnswer(String answerId, String replierId, String questionId) {
        Optional<Answer> optionalAnswer = this.answerRepository.findAll().
                stream().
                filter(answer2 -> answer2.getId().equals(answerId) &&
                        answer2.getReplierId().equals(replierId) && answer2.getQuestion().getId().equals(questionId)).
                findFirst();
        optionalAnswer.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        return optionalAnswer.get();
    }
}
