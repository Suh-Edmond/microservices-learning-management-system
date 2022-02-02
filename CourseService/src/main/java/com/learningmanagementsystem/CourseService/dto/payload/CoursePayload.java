package com.learningmanagementsystem.CourseService.dto.payload;


import com.learningmanagementsystem.CourseService.model.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.*;
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
