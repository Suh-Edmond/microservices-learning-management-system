package com.learningmanagementsystem.UserService.dto;


import lombok.Data;

@Data
public class AuthUserTokenDTO {

    private CustomUserDetailsDTO userDto;
    private String authToken;
    private String type;
    private String issuer;
    private String refreshToken;
    private long expiredIn;
    private String header;

}
