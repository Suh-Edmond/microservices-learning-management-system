package com.learningmanagementsystem.QuestionsAndAnswersService.util;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.QuestionDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.QuestionPayload;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Question;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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

    public Question getQuestionFromQuestionPayload(QuestionPayload questionPayload){
        Question question = new Question();
        question.setTopic(questionPayload.getTopic());
        question.setDetails(questionPayload.getDetails());
        if(questionPayload.getImage() != null){
            question.setImage(StringUtils.cleanPath(questionPayload.getImage().getOriginalFilename()));
        }
        return question;
    }

    public List<QuestionDto> getQuestionDtoList(@NotNull List<Question> questions){
        List<QuestionDto> questionDtos = questions.stream().map(question -> this.getQuestionDto(question)).collect(Collectors.toList());
        return questionDtos;
    }

    public QuestionDto getQuestionDto(Question question){
        QuestionDto questionDto = this.modelMapper.map(question, QuestionDto.class);
        return questionDto;
    }














}
