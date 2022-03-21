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

import java.util.ArrayList;
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
        UserDto userDto = this.userService.getUserFromUserService(userId, role);
        question.setId(this.util.generateId());
        question.setCourseId(courseDto.getId());
        question.setUserId(userDto.getId());
        question.setTopic(questionPayload.getTopic());
        question.setDetails(questionPayload.getDetails());
        question.setImage(this.util.getFileName(questionPayload.getFile()));
        this.questionRepository.save(question);
        this.fileStorageService.uploadCourseFiles(courseDto.getTitle(), fileCategory, questionPayload.getFile());
    }

    @Override
    public List<QuestionDto> getAllQuestions(String courseId) {
        List<Question> questions = this.questionRepository.findAll();
        List<QuestionDto> questionDtoList = new ArrayList<>();
        questions.stream().forEach(question -> {
            questionDtoList.add(this.generateQuestionDto(question));
        });
        return questionDtoList;
    }


    @Override
    public Question getQuestion(String questionId) {
        Optional<Question> question = this.questionRepository.findById(questionId);
        question.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        return question.get();
    }

    @Override
    public List<QuestionDto> getCourseQuestions(String courseId) {
        CourseDto courseDto = this.courseService.getCourseInfoFromCourseService(courseId);
        List<Question> questions = this.questionRepository.findByCourseId(courseDto.getId());
        List<QuestionDto> questionDtoList = new ArrayList<>();
        questions.stream().forEach(question -> {
            questionDtoList.add(this.generateQuestionDto(question));
        });
        return questionDtoList;
    }

    @Override
    public QuestionDto updateQuestion(QuestionPayload questionPayload, String courseId, String questionId,
                                   String userId,  ERole role) {
        CourseDto courseDto = this.courseService.getCourseInfoFromCourseService(courseId);
        UserDto userDto = this.userService.getUserFromUserService(userId, role);
        Question question = this.getQuestion(questionId);
        Question userQuestion = this.getUserQuestion(question.getId(), userDto.getId());
        userQuestion.setTopic(questionPayload.getTopic());
        userQuestion.setDetails(questionPayload.getDetails());
        userQuestion.setImage(this.util.getFileName(questionPayload.getFile()));
        Question updatedQuestion = this.questionRepository.saveAndFlush(userQuestion);
        this.fileStorageService.uploadCourseFiles(courseDto.getTitle(), QUESTIONS, questionPayload.getFile());
        QuestionDto updatedQuestionDto = this.generateQuestionDto(updatedQuestion);
        return updatedQuestionDto;
    }

    @Override
    public void deleteQuestion(String questionId, String userId, ERole role) {
        UserDto userDto = this.userService.getUserFromUserService(userId, role);
        Question question = this.getQuestion(questionId);
        Question userQuestion = this.getUserQuestion(question.getId(), userDto.getId());
        this.questionRepository.delete(userQuestion);
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


    public QuestionDto generateQuestionDto(Question question){
        QuestionDto questionDto = this.util.getQuestionDto(question);
        CourseDto  courseDto = this.courseService.getCourseInfoFromCourseService(question.getCourseId());
        UploadFileResponse uploadFileResponse = this.fileStorageService.getCourseMaterial(courseDto.getTitle(), QUESTIONS, question.getImage());
        questionDto.setImage(uploadFileResponse);
        return questionDto;
    }
}
