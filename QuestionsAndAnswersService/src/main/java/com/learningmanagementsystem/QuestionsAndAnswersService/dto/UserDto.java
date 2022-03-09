package com.learningmanagementsystem.QuestionsAndAnswersService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {

    private String Id;
    private String username;
    private String name;
    private String email;
    private String telephone;
    private String role;
}
