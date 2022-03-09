package com.learningmanagementsystem.QuestionsAndAnswersService.service.serviceImpl;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.*;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.payload.QuestionPayload;
import com.learningmanagementsystem.QuestionsAndAnswersService.exception.ForbiddenException;
import com.learningmanagementsystem.QuestionsAndAnswersService.exception.ResourceNotFoundException;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.FileCategory;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Question;
import com.learningmanagementsystem.QuestionsAndAnswersService.repository.QuestionRepository;
import com.learningmanagementsystem.QuestionsAndAnswersService.service.QuestionService;
import com.learningmanagementsystem.QuestionsAndAnswersService.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.learningmanagementsystem.QuestionsAndAnswersService.model.FileCategory.*;

@Service
public class QuestionServiceImpl implements QuestionService {

    private Util util = new Util();
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    CourseServiceImpl courseService;
    @Autowired
    FileStorageServiceImpl fileStorageService;

    @Override
    public void createQuestion(QuestionPayload questionPayload, String courseId, String userId, ERole role, FileCategory fileCategory) {
        Question question = new Question();
        CourseDto courseDto = this.courseService.getCourseInfoFromCourseService(courseId);
//        UserDto userDto = this.userService.getUserFromUserService(userId, role);
//        question.setId(this.util.generateId());
//        question.setCourseId(courseDto.getId());
//        question.setUserId(userDto.getId());
//        question.setTopic(questionPayload.getTopic());
//        question.setDetails(questionPayload.getDetails());
//        question.setImage(this.util.getFileName(questionPayload.getImage()));
//        this.questionRepository.save(question);
        System.out.println(courseDto.getTitle());
        this.fileStorageService.uploadCourseFiles(courseDto.getTitle(), fileCategory, questionPayload.getImage());
    }

    @Override
    public List<Question> getAllQuestions(String courseId) {
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
        CourseDto courseDto = this.courseService.getCourseInfoFromCourseService(courseId);
        List<Question> questions = this.questionRepository.findAll().
                stream().
                filter(question -> question.getCourseId().equals(courseDto.getId())).
                collect(Collectors.toList());
        return questions;
    }

    @Override
    public Question updateQuestion(QuestionPayload questionPayload, String courseId, String questionId,
                                   String userId,  ERole role) {
        CourseDto courseDto = this.courseService.getCourseInfoFromCourseService(courseId);
        UserDto userDto = this.userService.getUserFromUserService(userId, role);
        Question question1 = this.getQuestion(questionId);
        Question question2 = this.getUserQuestion(question1.getId(), userDto.getId());
        question2.setTopic(questionPayload.getTopic());
        question2.setDetails(questionPayload.getDetails());
        question2.setImage(this.util.getFileName(questionPayload.getImage()));
        Question updatedQuestion = this.questionRepository.saveAndFlush(question1);
        this.fileStorageService.uploadCourseFiles(courseDto.getTitle(), QUESTIONS, questionPayload.getImage());
        return updatedQuestion;
    }

    @Override
    public void deleteQuestion(String questionId, String userId, ERole role) {
        UserDto userDto = this.userService.getUserFromUserService(userId, role);
        Question question1 = this.getQuestion(questionId);
        Question question2 = this.getUserQuestion(question1.getId(), userDto.getId());
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
