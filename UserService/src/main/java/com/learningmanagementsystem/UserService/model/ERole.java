package com.learningmanagementsystem.UserService.model;

import java.util.HashMap;

public class ERole {

    public static final HashMap<String, String> USER_ROLES = new HashMap<>();
    public ERole(){
        USER_ROLES.put("student","ROLE_STUDENT");
        USER_ROLES.put("teacher", "ROLE_TEACHER");
        USER_ROLES.put("admin","ROLE_ADMIN");
    }
}
