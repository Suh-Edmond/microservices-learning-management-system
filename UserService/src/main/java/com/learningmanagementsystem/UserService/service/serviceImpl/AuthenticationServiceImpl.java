package com.learningmanagementsystem.UserService.service.serviceImpl;

import com.learningmanagementsystem.UserService.dto.CustomUserDetailsDTO;
import com.learningmanagementsystem.UserService.exception.NotFoundException;
import com.learningmanagementsystem.UserService.model.User;
import com.learningmanagementsystem.UserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(()-> new NotFoundException("No user found with username "+ username));
        return new CustomUserDetailsDTO(user.get());
    }
}
