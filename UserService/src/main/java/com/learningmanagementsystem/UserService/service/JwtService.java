package com.learningmanagementsystem.UserService.service;

public interface JwtService {

    String getUserName(String token);
    String generateToken(String username);
    Boolean validateToken(String jwtToken);
    long getTokenExpiration();
}
