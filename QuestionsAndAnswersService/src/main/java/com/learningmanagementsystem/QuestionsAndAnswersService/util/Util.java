package com.learningmanagementsystem.QuestionsAndAnswersService.util;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.AnswerDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.payload.AnswerPayload;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.QuestionDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.payload.QuestionPayload;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Answer;
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

    public Answer getAnswerFromAnswerPayload(AnswerPayload payload){
        Answer answer = new Answer();
        answer.setResponse(payload.getResponse());
        answer.setDetails(payload.getDetails());
        if(payload.getImage() != null){
            answer.setImage(StringUtils.cleanPath(payload.getImage().getOriginalFilename()));
        }
        return answer;
    }

    public List<QuestionDto> getQuestionDtoList(@NotNull List<Question> questions){
        List<QuestionDto> questionDtos = questions.stream().map(question -> this.getQuestionDto(question)).collect(Collectors.toList());
        return questionDtos;
    }

    public QuestionDto getQuestionDto(Question question){
        QuestionDto questionDto = this.modelMapper.map(question, QuestionDto.class);
        return questionDto;
    }

    public AnswerDto getAnswerDto(Answer answer) {
        AnswerDto answerDto = this.modelMapper.map(answer, AnswerDto.class);
        return  answerDto;
    }

    public List<AnswerDto>  getAnswerDtoList(List<Answer> answerList) {
        List<AnswerDto> anQuestionDtoList = answerList.stream().map(answer -> this.getAnswerDto(answer)).collect(Collectors.toList());
        return anQuestionDtoList;
    }















}
