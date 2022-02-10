package com.learningmanagementsystem.QuestionsAndAnswersService.controller;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.AnswerDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.payload.AnswerPayload;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Answer;
import com.learningmanagementsystem.QuestionsAndAnswersService.service.serviceImpl.AnswerServiceImpl;
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

@RequestMapping("/api/v1/")
@RestController
public class AnswerController {

    @Autowired
    private AnswerServiceImpl answerService;
    private Util util = new Util();

    @PostMapping(value = "protected/questions/{questionId}/answers", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MessageResponse> createAnswer(@Valid @ModelAttribute AnswerPayload answerPayload, @PathVariable String questionId) {
        Answer answer = this.util.getAnswerFromAnswerPayload(answerPayload);
        this.answerService.createAnswer(answer, questionId);
        return new ResponseEntity<>(new MessageResponse("success", "Create answer successfully", new Date()), HttpStatus.CREATED);
    }

    @GetMapping("protected/questions/answers")
    public ResponseEntity<List<AnswerDto>> getAllQuestionsAnswer(@RequestParam("questionId") String questionId){
        List<Answer> answers = this.answerService.getAllQuestionAnswers(questionId);
        List<AnswerDto> answerDtoList = this.util.getAnswerDtoList(answers);
        return new ResponseEntity<>(answerDtoList, HttpStatus.OK);
    }

    @GetMapping("protected/questions/{questionId}/answers/{answerId}")
    public ResponseEntity<AnswerDto> getQuestionAnswer(@PathVariable("answerId") String answerId, @PathVariable("questionId") String questionId) {
        Answer answer = this.answerService.getQuestionAnswer(answerId, questionId);
        AnswerDto answerDto = this.util.getAnswerDto(answer);
        return new ResponseEntity<>(answerDto, HttpStatus.OK);
    }

    @PostMapping(value = "protected/questions/{questionId}/answers/{answerId}/repliers/{replierId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<AnswerDto> updateAnswer(@Valid @ModelAttribute AnswerPayload answerPayload, @PathVariable String questionId, @PathVariable String answerId, @PathVariable String replierId){
        Answer answer = this.util.getAnswerFromAnswerPayload(answerPayload);
        Answer answerUpdated = this.answerService.updateAnswer(answer, questionId, answerId, replierId);
        AnswerDto answerDto = this.util.getAnswerDto(answerUpdated);
        return new ResponseEntity<>(answerDto, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "protected/questions/answers/repliers")
    public  ResponseEntity<?> deleteAnswer(@RequestParam String questionId, @RequestParam String answerId, @RequestParam String replierId){
        this.answerService.deleteAnswer(questionId, answerId, replierId);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "protected/answers/repliers")
    public ResponseEntity<List<AnswerDto>> getAllReplierAnswers(@RequestParam String replierId){
        List<Answer> answers = this.answerService.getAllAnswersByReplier(replierId);
        List<AnswerDto> answerDtoList = this.util.getAnswerDtoList(answers);
        return new ResponseEntity<>(answerDtoList, HttpStatus.OK);
    }

}
