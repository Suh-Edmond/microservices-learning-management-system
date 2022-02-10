package com.learningmanagementsystem.QuestionsAndAnswersService.dto.payload;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel
public class QuestionPayload {

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String topic;
    @NotNull
    @NotBlank
    private String details;
    private MultipartFile image;
}
