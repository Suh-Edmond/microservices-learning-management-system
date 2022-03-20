package com.learningmanagementsystem.QuestionsAndAnswersService.controller;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.AnswerDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.ERole;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.payload.AnswerPayload;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.FileCategory;
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

@RequestMapping("/QUESTION-AND-ANSWER-SERVICE/api/v1/")
@RestController
public class AnswerController {

    @Autowired
    private AnswerServiceImpl answerService;
    private Util util = new Util();

    @PostMapping(value = "protected/users/{replierId}/roles/{role}/questions/{questionId}/answers/fileCategory/{fileCategory}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MessageResponse> createAnswer(@Valid @ModelAttribute AnswerPayload answerPayload,
                                                        @PathVariable String questionId,
                                                        @PathVariable String replierId,
                                                        @PathVariable ERole role,
                                                        @PathVariable FileCategory fileCategory) {
        this.answerService.createAnswer(answerPayload, questionId, replierId, role, fileCategory);
        return new ResponseEntity<>(new MessageResponse("success", "Create answer successfully", new Date()), HttpStatus.CREATED);
    }

    @GetMapping("protected/questions/answers")
    public ResponseEntity<List<AnswerDto>> getAllQuestionsAnswer(@RequestParam("questionId") String questionId){
        List<AnswerDto> answerDtoList = this.answerService.getAllQuestionAnswers(questionId);
        return new ResponseEntity<>(answerDtoList, HttpStatus.OK);
    }

    @GetMapping("protected/questions/{questionId}/answers/{answerId}")
    public ResponseEntity<AnswerDto> getQuestionAnswer(@PathVariable("answerId") String answerId, @PathVariable("questionId") String questionId) {
        AnswerDto answerDto = this.answerService.getQuestionAnswer(answerId, questionId);
        return new ResponseEntity<>(answerDto, HttpStatus.OK);
    }

    @PostMapping(value = "protected/repliers/{replierId}/roles/{role}/questions/{questionId}/answers/{answerId}/fileCategory/{fileCategory}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<AnswerDto> updateAnswer(@Valid @ModelAttribute AnswerPayload answerPayload,
                                                  @PathVariable String questionId,
                                                  @PathVariable String answerId,
                                                  @PathVariable String replierId,
                                                  @PathVariable ERole role,
                                                  @PathVariable FileCategory fileCategory){
        AnswerDto answerUpdated = this.answerService.updateAnswer(answerPayload, questionId, answerId, replierId, role, fileCategory);
        return new ResponseEntity<>(answerUpdated, HttpStatus.ACCEPTED);
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
        List<AnswerDto> answerDtoList = this.answerService.getAllAnswersByReplier(replierId, role);
        return new ResponseEntity<>(answerDtoList, HttpStatus.OK);
    }

}
