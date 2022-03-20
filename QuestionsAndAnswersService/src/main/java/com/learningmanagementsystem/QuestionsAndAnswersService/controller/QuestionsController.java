package com.learningmanagementsystem.QuestionsAndAnswersService.controller;


import com.learningmanagementsystem.QuestionsAndAnswersService.dto.ERole;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.QuestionDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.payload.QuestionPayload;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.FileCategory;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Question;
import com.learningmanagementsystem.QuestionsAndAnswersService.service.serviceImpl.FileStorageServiceImpl;
import com.learningmanagementsystem.QuestionsAndAnswersService.service.serviceImpl.QuestionServiceImpl;
import com.learningmanagementsystem.QuestionsAndAnswersService.util.MessageResponse;
import com.learningmanagementsystem.QuestionsAndAnswersService.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/QUESTION-AND-ANSWER-SERVICE/api/v1/")
public class QuestionsController {

    @Autowired
    private QuestionServiceImpl questionService;
    private Util util = new Util();
    @Autowired
    FileStorageServiceImpl fileStorageService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "protected/users/{userId}/roles/{role}/courses/{courseId}/questions/{fileCategory}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MessageResponse> createQuestion(@Valid @ModelAttribute  QuestionPayload questionPayload,
                                                          @PathVariable("userId") String userId,
                                                          @PathVariable("role") ERole role,
                                                          @PathVariable("courseId") String courseId,
                                                          @PathVariable("fileCategory") FileCategory fileCategory){
        this.questionService.createQuestion(questionPayload, courseId, userId, role, fileCategory);
        return new ResponseEntity<>(new MessageResponse("success", "Create question successfully", new Date()), HttpStatus.CREATED);
    }

    @GetMapping("public/courses/questions-all")
    public ResponseEntity<List<QuestionDto>> getAllQuestions(@RequestParam("courseId") String courseId){
        List<QuestionDto> questionDtoList = this.questionService.getAllQuestions(courseId);
        return new ResponseEntity<>(questionDtoList, HttpStatus.OK);
    }
    @GetMapping("public/questions")
    public ResponseEntity<QuestionDto> getQuestion(@RequestParam("questionId") String questionId){
        Question question = this.questionService.getQuestion(questionId);
        QuestionDto questionDto = this.questionService.generateQuestionDto(question);
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }

    @GetMapping("public/courses/questions")
    public ResponseEntity<List<QuestionDto>> getCourseQuestions(@RequestParam("courseId") String courseId){
        List<QuestionDto> questionDtoList = this.questionService.getCourseQuestions(courseId);
        return new ResponseEntity<>(questionDtoList, HttpStatus.OK);
    }

    @PostMapping(path = "protected/users/{userId}/roles/{role}/courses/{courseId}/questions/{questionId}/{fileCategory}/update/" , consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public  ResponseEntity<QuestionDto> updateCourseQuestion(@Valid @ModelAttribute QuestionPayload questionPayload,
                                                             @PathVariable("questionId") String questionId,
                                                             @PathVariable("courseId") String courseId,
                                                             @PathVariable("userId") String userId,
                                                             @PathVariable("role") ERole role,
                                                             @PathVariable("fileCategory") FileCategory fileCategory){
        QuestionDto questionDto = this.questionService.updateQuestion(questionPayload, courseId, questionId, userId, role);
        return new ResponseEntity<>(questionDto, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("protected/courses/questions")
    public ResponseEntity<?> deleteQuestion(@RequestParam("questionId") String questionId,
                                            @RequestParam("userId") String userId,
                                            @RequestParam("role") ERole role){
        this.questionService.deleteQuestion(questionId, userId, role);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
