package com.learningmanagementsystem.CourseService.dto.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CoursePayload {



    @NotNull
    @NotBlank
    @Size(min = 4)
    private String title;

    @NotNull
    @NotBlank
    private String level;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @DecimalMin(value = "1.0")
    private BigDecimal price;


}
