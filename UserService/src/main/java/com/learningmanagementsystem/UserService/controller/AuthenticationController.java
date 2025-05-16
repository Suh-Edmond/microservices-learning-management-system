package com.learningmanagementsystem.UserService.controller;

import com.learningmanagementsystem.UserService.dto.AuthUserTokenDTO;
import com.learningmanagementsystem.UserService.dto.LoginUserDTO;
import com.learningmanagementsystem.UserService.dto.RegisterUserPayload;
import com.learningmanagementsystem.UserService.dto.SuccessResponse;
import com.learningmanagementsystem.UserService.model.User;
import com.learningmanagementsystem.UserService.service.serviceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@CrossOrigin()
@RequestMapping("/api/v1/public/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserServiceImpl userService;
    private final ModelMapper modelMapper;


    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> registerUser( @Valid @RequestBody RegisterUserPayload registerUserPayload){
        User user = modelMapper.map(registerUserPayload, User.class);
        userService.createUser(user);
        return new ResponseEntity<>(new SuccessResponse("success", new Date()), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthUserTokenDTO> loginUser(@Valid @RequestBody LoginUserDTO loginUserDTO){
        return  ResponseEntity.ok(userService.loginUser(loginUserDTO));
    }

}
