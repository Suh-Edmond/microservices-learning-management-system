package com.learningmanagementsystem.UserService.Utils;

import java.util.Locale;
import java.util.UUID;

public class Utils {


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

}
