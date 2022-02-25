package com.learningmanagementsystem.QuestionsAndAnswersService.controller;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.AnswerDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.ERole;
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

    @PostMapping(value = "protected/users/{replierId}/roles/{role}/questions/{questionId}/answers", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MessageResponse> createAnswer(@Valid @ModelAttribute AnswerPayload answerPayload,
                                                        @PathVariable String questionId,
                                                        @PathVariable String replierId,
                                                        @PathVariable ERole role) {
        Answer answer = this.util.getAnswerFromAnswerPayload(answerPayload);
        this.answerService.createAnswer(answer, questionId, replierId, role);
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

    @PostMapping(value = "protected/repliers/{replierId}/roles/{role}/questions/{questionId}/answers/{answerId}/", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<AnswerDto> updateAnswer(@Valid @ModelAttribute AnswerPayload answerPayload,
                                                  @PathVariable String questionId,
                                                  @PathVariable String answerId,
                                                  @PathVariable String replierId,
                                                  @PathVariable ERole role){
        Answer answer = this.util.getAnswerFromAnswerPayload(answerPayload);
        Answer answerUpdated = this.answerService.updateAnswer(answer, questionId, answerId, replierId, role);
        AnswerDto answerDto = this.util.getAnswerDto(answerUpdated);
        return new ResponseEntity<>(answerDto, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "protected/questions/answers/repliers")
    public  ResponseEntity<?> deleteAnswer(@RequestParam String questionId,
                                           @RequestParam String answerId,
                                           @RequestParam String replierId,
                                           @RequestParam ERole role){
        this.answerService.deleteAnswer(questionId, answerId, replierId, role);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "protected/answers/repliers")
    public ResponseEntity<List<AnswerDto>> getAllReplierAnswers(@RequestParam String replierId, @RequestParam ERole role){
        List<Answer> answers = this.answerService.getAllAnswersByReplier(replierId, role);
        List<AnswerDto> answerDtoList = this.util.getAnswerDtoList(answers);
        return new ResponseEntity<>(answerDtoList, HttpStatus.OK);
    }

}
