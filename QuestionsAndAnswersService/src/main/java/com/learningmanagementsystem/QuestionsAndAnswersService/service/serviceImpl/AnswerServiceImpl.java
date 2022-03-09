package com.learningmanagementsystem.QuestionsAndAnswersService.service.serviceImpl;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.CourseDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.ERole;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.UserDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.payload.AnswerPayload;
import com.learningmanagementsystem.QuestionsAndAnswersService.exception.ResourceNotFoundException;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Answer;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.FileCategory;
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
    @Autowired
    UserServiceImpl userService;
    @Autowired
    FileStorageServiceImpl fileStorageService;
    @Autowired
    CourseServiceImpl courseService;


    @Override
    public void createAnswer(AnswerPayload answerPayload, String questionId, String replierId, ERole role, FileCategory fileCategory) {
        Answer answer = new Answer();
        UserDto userDto = this.userService.getUserFromUserService(replierId, role);
        Question question = this.questionService.getQuestion(questionId);
        CourseDto courseDto = this.courseService.getCourseInfoFromCourseService(question.getCourseId());
        answer.setId(this.util.generateId());
        answer.setReplierId(userDto.getId());
        answer.setResponse(answerPayload.getResponse());
        answer.setDetails(answerPayload.getDetails());
        answer.setImage(this.util.getFileName(answerPayload.getImage()));
        answer.setQuestion(question);
        question.getCourseId();
        this.answerRepository.save(answer);
        this.fileStorageService.uploadCourseFiles(courseDto.getTitle(), fileCategory, answerPayload.getImage());
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
    public Answer updateAnswer(AnswerPayload answerPayload, String questionId, String answerId, String replierId,  ERole role, FileCategory fileCategory) {
        UserDto userDto = this.userService.getUserFromUserService(replierId, role);
        Answer answer1 = this.getReplierAnswer(answerId, userDto.getId(), role, questionId);
        Question question = this.questionService.getQuestion(questionId);
        CourseDto courseDto = this.courseService.getCourseInfoFromCourseService(question.getCourseId());
        answer1.setDetails(answerPayload.getDetails());
        answer1.setResponse(answerPayload.getResponse());
        answer1.setImage(this.util.getFileName(answerPayload.getImage()));
        this.answerRepository.save(answer1);
        this.fileStorageService.uploadCourseFiles(courseDto.getTitle(), fileCategory, answerPayload.getImage());
        return answer1;
    }

    @Override
    public List<Answer> getAllAnswersByReplier(String replierId, ERole role) {
        UserDto userDto = this.userService.getUserFromUserService(replierId, role);
        List<Answer> answerList = this.answerRepository.findAll().
                stream().
                filter(answer -> answer.getReplierId().equals(userDto.getId())).
                collect(Collectors.toList());
        return answerList;
    }

    @Override
    public void deleteAnswer(String questionId, String answerId, String replierId, ERole role) {
        UserDto userDto = this.userService.getUserFromUserService(replierId, role);
        Answer answer = this.getReplierAnswer(answerId, userDto.getId(), role,  questionId);
        this.answerRepository.delete(answer);
    }

    @Override
    public Answer getReplierAnswer(String answerId, String replierId, ERole role, String questionId) {
        UserDto userDto = this.userService.getUserFromUserService(replierId, role);
        Optional<Answer> optionalAnswer = this.answerRepository.findAll().
                stream().
                filter(answer2 -> answer2.getId().equals(answerId) &&
                        answer2.getReplierId().equals(userDto.getId()) && answer2.getQuestion().getId().equals(questionId)).
                findFirst();
        optionalAnswer.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        return optionalAnswer.get();
    }
}
