package com.learningmanagementsystem.UserService.service.serviceImpl;

import com.learningmanagementsystem.UserService.Utils.Utils;
import com.learningmanagementsystem.UserService.exception.CustomizedBadCredentialsException;
import com.learningmanagementsystem.UserService.model.ERole;
import com.learningmanagementsystem.UserService.model.User;
import com.learningmanagementsystem.UserService.repository.UserRepository;
import com.learningmanagementsystem.UserService.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    private Utils utils = new Utils();

    @Override
    public void createUser(User user) {
        Boolean userNameExist = this.userRepository.existsByUsername(user.getUsername());
        Boolean userEmailExist = this.userRepository.existsByEmail(user.getEmail());
        if(userNameExist){
            throw new CustomizedBadCredentialsException("User name already exist");
        }
        if(userEmailExist){
            throw new CustomizedBadCredentialsException("User email already exist");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Boolean checkConfirmPassword = utils.checkConfirmPassword(user.getPassword(), user.getConfirmPassword());
        user.setRole((user.getRole()));
        if(!checkConfirmPassword){
            throw new CustomizedBadCredentialsException("Password mismatch");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setId(utils.generateUserId());
        userRepository.save(user);
    }
}
