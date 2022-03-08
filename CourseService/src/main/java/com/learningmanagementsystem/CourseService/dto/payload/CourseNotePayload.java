package com.learningmanagementsystem.CourseService.dto.payload;


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
public class CourseNotePayload {

    @NotNull
    @NotBlank
    @Size(min = 4, max = 255)
    private String topic;

    @NotNull
    private MultipartFile file;
}
