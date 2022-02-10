package com.learningmanagementsystem.QuestionsAndAnswersService.dto.payload;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel
public class AnswerPayload {

    @NotNull
    @NotBlank
    private String response;
    @NotNull
    @NotBlank
    private String details;
    @NotNull
    @NotBlank
    private String replierId;
    private MultipartFile image;
}
