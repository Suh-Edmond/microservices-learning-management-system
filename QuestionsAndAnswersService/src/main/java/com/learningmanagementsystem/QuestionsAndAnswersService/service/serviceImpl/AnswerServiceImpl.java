package com.learningmanagementsystem.QuestionsAndAnswersService.service.serviceImpl;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.*;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.learningmanagementsystem.QuestionsAndAnswersService.model.FileCategory.ANSWERS;
import static com.learningmanagementsystem.QuestionsAndAnswersService.model.FileCategory.QUESTIONS;


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
        this.answerRepository.save(answer);
        this.fileStorageService.uploadCourseFiles(courseDto.getTitle(), fileCategory, answerPayload.getImage());
    }

    @Override
    public List<AnswerDto> getAllQuestionAnswers(String questionId) {
        List<Answer> answers = this.answerRepository.findAll().stream().filter(answer ->
            answer.getQuestion().getId().equals(questionId)
        ).collect(Collectors.toList());
        List<AnswerDto> answerDtoList = new ArrayList<>();
        answers.stream().forEach(answer -> {
            answerDtoList.add(this.generateAnswerDto(answer));
        });
        return answerDtoList;
    }

    @Override
    public AnswerDto getQuestionAnswer(String answerId, String questionId) {
        Optional<Answer> answerOptional = this.answerRepository.findAll().
                stream().
                filter(answer -> answer.getId().equals(answerId) &&
                        answer.getQuestion().getId().equals(questionId)).
                findFirst();
        answerOptional.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        AnswerDto answerDto = this.generateAnswerDto(answerOptional.get());
        return answerDto;
    }

    @Override
    public AnswerDto updateAnswer(AnswerPayload answerPayload, String questionId, String answerId, String replierId,  ERole role, FileCategory fileCategory) {
        UserDto userDto = this.userService.getUserFromUserService(replierId, role);
        Answer updatedAnswer = this.getReplierAnswer(answerId, userDto.getId(), role, questionId);
        Question question = this.questionService.getQuestion(questionId);
        CourseDto courseDto = this.courseService.getCourseInfoFromCourseService(question.getCourseId());
        updatedAnswer.setDetails(answerPayload.getDetails());
        updatedAnswer.setResponse(answerPayload.getResponse());
        updatedAnswer.setImage(this.util.getFileName(answerPayload.getImage()));
        this.answerRepository.save(updatedAnswer);
        this.fileStorageService.uploadCourseFiles(courseDto.getTitle(), fileCategory, answerPayload.getImage());
        AnswerDto answerDto = this.generateAnswerDto(updatedAnswer);
        return answerDto;
    }

    @Override
    public List<AnswerDto> getAllAnswersByReplier(String replierId, ERole role) {
        UserDto userDto = this.userService.getUserFromUserService(replierId, role);
        List<Answer> answerList = this.answerRepository.findByReplierId(userDto.getId());
        List<AnswerDto> answerDtoList = new ArrayList<>();
        answerList.stream().forEach(answer -> {
            answerDtoList.add(this.generateAnswerDto(answer));
        });
        return answerDtoList;
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

    public AnswerDto generateAnswerDto(Answer answer){
        Question question = this.questionService.getQuestion(answer.getQuestion().getId());
        AnswerDto answerDto = this.util.getAnswerDto(answer);
        CourseDto courseDto = this.courseService.getCourseInfoFromCourseService(question.getCourseId());
        UploadFileResponse uploadFileResponse = this.fileStorageService.getCourseMaterial(courseDto.getTitle(), ANSWERS, answer.getImage());
        answerDto.setImage(uploadFileResponse);
        return answerDto;
    }
}
