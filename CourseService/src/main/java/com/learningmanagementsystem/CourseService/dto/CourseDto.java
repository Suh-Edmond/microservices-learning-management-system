package com.learningmanagementsystem.CourseService.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseDto {

    private String Id;
    private String title;
    private String level;
    private String description;
    private UploadFileResponse courseImage;
    private BigDecimal price;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
