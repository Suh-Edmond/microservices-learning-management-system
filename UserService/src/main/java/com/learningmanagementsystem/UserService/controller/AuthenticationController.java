package com.learningmanagementsystem.UserService.controller;

import com.learningmanagementsystem.UserService.Utils.Utils;
import com.learningmanagementsystem.UserService.dto.RegisterUserPayload;
import com.learningmanagementsystem.UserService.dto.SuccessResponse;
import com.learningmanagementsystem.UserService.model.User;
import com.learningmanagementsystem.UserService.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@CrossOrigin()
@RequestMapping("/api/v1/public/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private ModelMapper modelMapper;
    private Utils utils;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> registerUser( @Valid @RequestBody RegisterUserPayload registerUserPayload){
        User user = this.modelMapper.map(registerUserPayload, User.class);
        authenticationService.createUser(user);
        return new ResponseEntity<>(new SuccessResponse("success", new Date()), HttpStatus.CREATED);
    }

}
