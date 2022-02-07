package com.learningmanagementsystem.UserService.Utils;

import com.learningmanagementsystem.UserService.dto.UserDto;
import com.learningmanagementsystem.UserService.model.User;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

public class Utils {

    private ModelMapper modelMapper;
    public Utils(){
        this.modelMapper = new ModelMapper();
    }
    public String generateUserId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public Boolean checkConfirmPassword(String password, String confirmPassword) {
        if(password.equals(confirmPassword)){
            return true;
        }
        return false;
    }
    public List<UserDto> getListUserDto(List<User> users){
        List<UserDto> userDtos = users.
                stream().
                map(user-> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }

}
