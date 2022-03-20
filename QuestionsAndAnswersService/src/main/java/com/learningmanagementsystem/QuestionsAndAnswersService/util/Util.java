package com.learningmanagementsystem.QuestionsAndAnswersService.util;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.AnswerDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.QuestionDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Answer;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Question;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class Util {

    private ModelMapper modelMapper;
    public Util(){
        this.modelMapper = new ModelMapper();
    }

    public String generateId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }


    public QuestionDto getQuestionDto(Question question){
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setDetails(question.getDetails());
        questionDto.setCourseId(question.getCourseId());
        questionDto.setUserId(question.getUserId());
        questionDto.setTopic(question.getTopic());
        questionDto.setCreatedAt(question.getCreatedAt());
        questionDto.setUpdatedAt(question.getUpdatedAt());
        return questionDto;
    }



    public AnswerDto getAnswerDto(Answer answer) {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setId(answer.getId());
        answerDto.setDetails(answer.getDetails());
        answerDto.setResponse(answer.getResponse());
        answerDto.setReplierId(answer.getReplierId());
        answerDto.setQuestionId(answer.getQuestion().getId());
        answerDto.setCreatedAt(answer.getCreatedAt());
        answerDto.setUpdatedAt(answer.getUpdatedAt());
        return  answerDto;
    }



    public String getFileName(MultipartFile multipartFile){
        String fileName = null;
        if(multipartFile != null){
            fileName =  StringUtils.cleanPath(multipartFile.getOriginalFilename());
        }
        return fileName;
    }

















}
