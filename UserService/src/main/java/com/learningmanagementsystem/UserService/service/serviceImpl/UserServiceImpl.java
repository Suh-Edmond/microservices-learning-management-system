package com.learningmanagementsystem.UserService.service.serviceImpl;

import com.learningmanagementsystem.UserService.Utils.Utils;
import com.learningmanagementsystem.UserService.dto.AuthUserTokenDTO;
import com.learningmanagementsystem.UserService.dto.CustomUserDetailsDTO;
import com.learningmanagementsystem.UserService.dto.LoginUserDTO;
import com.learningmanagementsystem.UserService.exception.CustomizedBadCredentialsException;
import com.learningmanagementsystem.UserService.exception.NotFoundException;
import com.learningmanagementsystem.UserService.model.ERole;
import com.learningmanagementsystem.UserService.model.User;
import com.learningmanagementsystem.UserService.repository.UserRepository;
import com.learningmanagementsystem.UserService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CourseServiceImpl courseService;
    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtService;

    @Override
    public List<User> getAllTeachers() {
        List<User> users = this.userRepository.
                findAll().
                stream().
                filter(user -> user.getRole().equals(ERole.ROLE_TEACHER.toString())).
                collect(Collectors.toList());
        return users;
    }

    @Override
    public List<User> getAllStudents() {
        List<User> users = this.userRepository.
                findAll().
                stream().
                filter(user -> user.getRole().equals(ERole.ROLE_STUDENT.toString())).
                collect(Collectors.toList());
        return users;
    }

    @Override
    public List<User> getAllStudentWithInfoEnrolledCourse(String courseId) {
        List<String> studentIds = this.courseService.getAllStudentsEnrolledToCourseFromCourseService(courseId);
        List<User> students = studentIds.stream().map(id -> this.getStudent(id)).collect(Collectors.toList());
        return students;
    }

    @Override
    public void deleteUser(String userId, String role) {
        User user = this.getUser(userId, role);
        this.userRepository.delete(user);
    }

    @Override
    public User getUser(String userId, String role) {
        Optional<User> user = this.userRepository.findAll().stream().filter(user1 -> user1.getId().equals(userId) && user1.getRole().equals(role)).findFirst();
        user.orElseThrow(() -> new NotFoundException("Resource not found"));
        return user.get();
    }

    public User getStudent(String userId){
        Optional<User> user = this.userRepository.findAll().
                stream().
                filter(user1 -> user1.getId().equals(userId) && user1.getRole().equals(ERole.ROLE_STUDENT.toString())).
                findFirst();
        user.orElseThrow(() -> new NotFoundException("Resource not found"));
        return user.get();
    }

    @Override
    public User getTeacher(String userId) {
        Optional<User> user = this.userRepository.findAll().
                stream().
                filter(user1 -> user1.getId().equals(userId) && user1.getRole().equals(ERole.ROLE_TEACHER.toString())).
                findFirst();
        user.orElseThrow(() -> new NotFoundException("Resource not found"));
        return user.get();
    }
    @Override
    public void createUser(User user) {
        Boolean userNameExist = userRepository.existsByUsername(user.getUsername());
        Boolean userEmailExist = userRepository.existsByEmail(user.getEmail());
        if(userNameExist){
            throw new CustomizedBadCredentialsException("User name already exist");
        }
        if(userEmailExist){
            throw new CustomizedBadCredentialsException("User email already exist");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Boolean checkConfirmPassword = Utils.checkConfirmPassword(user.getPassword(), user.getConfirmPassword());
        user.setRole((user.getRole()));
        if(!checkConfirmPassword){
            throw new CustomizedBadCredentialsException("Password mismatch");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setId(Utils.generateUserId());
        userRepository.save(user);
    }

    @Override
    public AuthUserTokenDTO loginUser(LoginUserDTO loginUserDTO) {
        AuthUserTokenDTO authUserTokenDTO = new AuthUserTokenDTO();
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.getUsername(), loginUserDTO.getPassword()));
        }catch (BadCredentialsException exception){
            throw new CustomizedBadCredentialsException("Invalid username or password");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(loginUserDTO.getUsername());
        CustomUserDetailsDTO userDetailsDTO = (CustomUserDetailsDTO) authentication.getPrincipal();
        authUserTokenDTO.setUserDto(userDetailsDTO);
        authUserTokenDTO.setAuthToken(jwt);
        authUserTokenDTO.setHeader("Authorization");
        authUserTokenDTO.setIssuer("USER-SERVICE");
        authUserTokenDTO.setType("Bearer");
        authUserTokenDTO.setExpiredIn(jwtService.getTokenExpiration());
        return authUserTokenDTO;
    }
}
