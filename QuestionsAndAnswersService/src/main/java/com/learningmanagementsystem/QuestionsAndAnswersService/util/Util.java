package com.learningmanagementsystem.QuestionsAndAnswersService.util;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.AnswerDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.CourseDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.QuestionDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.UploadFileResponse;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Answer;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.FileCategory;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.Question;
import com.learningmanagementsystem.QuestionsAndAnswersService.service.serviceImpl.CourseServiceImpl;
import com.learningmanagementsystem.QuestionsAndAnswersService.service.serviceImpl.FileStorageServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.learningmanagementsystem.QuestionsAndAnswersService.model.FileCategory.QUESTIONS;

@Component
public class Util {

    private ModelMapper modelMapper;
    @Autowired
    CourseServiceImpl courseService;
    @Autowired
    FileStorageServiceImpl fileStorageService;
    public Util(){
        this.modelMapper = new ModelMapper();
    }

    public String generateId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }



    public List<QuestionDto> getQuestionDtoList(@NotNull List<Question> questions){
        List<QuestionDto> questionDtos = questions.stream().map(question -> this.getQuestionDto(question)).collect(Collectors.toList());
        return questionDtos;
    }

    public QuestionDto getQuestionDto(Question question){
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setDetails(question.getDetails());
        questionDto.setCourseId(question.getCourseId());
        questionDto.setUserId(question.getUserId());
        questionDto.setTopic(question.getTopic());
        questionDto.setAnswers(question.getAnswers());
        questionDto.setCreatedAt(question.getCreatedAt());
        questionDto.setUpdatedAt(question.getUpdatedAt());
        CourseDto courseDto = this.courseService.getCourseInfoFromCourseService(question.getCourseId());
        UploadFileResponse response = this.fileStorageService.getCourseImage(courseDto.getTitle(), QUESTIONS, question.getImage());
        questionDto.setImage(response);
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
        CourseDto courseDto = this.courseService.getCourseInfoFromCourseService(answer.getQuestion().getCourseId());
        UploadFileResponse response = this.fileStorageService.getCourseImage(courseDto.getTitle(), FileCategory.ANSWERS, answer.getImage());
        answerDto.setImage(response);
        return  answerDto;
    }

    public List<AnswerDto>  getAnswerDtoList(List<Answer> answerList) {
        List<AnswerDto> anQuestionDtoList = answerList.stream().map(answer -> this.getAnswerDto(answer)).collect(Collectors.toList());
        return anQuestionDtoList;
    }

    public String getFileName(MultipartFile multipartFile){
        String fileName = null;
        if(multipartFile != null){
            fileName =  StringUtils.cleanPath(multipartFile.getOriginalFilename());
        }
        return fileName;
    }

















}
