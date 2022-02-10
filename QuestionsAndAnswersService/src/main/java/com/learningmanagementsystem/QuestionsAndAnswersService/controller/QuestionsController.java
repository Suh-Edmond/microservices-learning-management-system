package com.learningmanagementsystem.QuestionsAndAnswersService.controller;


import com.learningmanagementsystem.QuestionsAndAnswersService.dto.QuestionDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.payload.QuestionPayload;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Question;
import com.learningmanagementsystem.QuestionsAndAnswersService.service.serviceImpl.QuestionServiceImpl;
import com.learningmanagementsystem.QuestionsAndAnswersService.util.MessageResponse;
import com.learningmanagementsystem.QuestionsAndAnswersService.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class QuestionsController {

    @Autowired
    private QuestionServiceImpl questionService;
    private Util util = new Util();

    @PostMapping(path = "protected/courses/questions", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MessageResponse> createQuestion(@Valid @ModelAttribute  QuestionPayload questionPayload,
                                                          @RequestParam("courseId") String courseId, @RequestParam("userId") String userId){
        Question question = this.util.getQuestionFromQuestionPayload(questionPayload);
        this.questionService.createQuestion(question, courseId, userId);
        return new ResponseEntity<>(new MessageResponse("success", "Create question successfully", new Date()), HttpStatus.CREATED);
    }

    @GetMapping("public/questions-all")
    public ResponseEntity<List<QuestionDto>> getAllQuestions(){
        List<Question> questions = this.questionService.getAllQuestions();
        List<QuestionDto> questionDtoList = this.util.getQuestionDtoList(questions);
        return new ResponseEntity<>(questionDtoList, HttpStatus.OK);
    }
    @GetMapping("public/questions")
    public ResponseEntity<QuestionDto> getQuestion(@RequestParam("questionId") String questionId){
        Question question = this.questionService.getQuestion(questionId);
        QuestionDto questionDto = this.util.getQuestionDto(question);
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }

    @GetMapping("public/courses/questions")
    public ResponseEntity<List<QuestionDto>> getCourseQuestions(@RequestParam("courseId") String courseId){
        List<Question> questions = this.questionService.getCourseQuestions(courseId);
        List<QuestionDto> questionDtoList = this.util.getQuestionDtoList(questions);
        return new ResponseEntity<>(questionDtoList, HttpStatus.OK);
    }

    @PostMapping(path = "protected/courses/questions-update" , consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public  ResponseEntity<QuestionDto> updateCourseQuestion(@Valid @ModelAttribute QuestionPayload questionPayload,
                                                             @RequestParam("questionId") String questionId, @RequestParam("userId") String userId){
        Question question = this.util.getQuestionFromQuestionPayload(questionPayload);
        Question updatedQuestion = this.questionService.updateQuestion(question, questionId, userId);
        QuestionDto questionDto = this.util.getQuestionDto(updatedQuestion);
        return new ResponseEntity<>(questionDto, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("protected/courses/questions")
    public ResponseEntity<?> deleteQuestion(@RequestParam("questionId") String questionId, @RequestParam("userId") String userId){
        this.questionService.deleteQuestion(questionId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
