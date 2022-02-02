package com.learningmanagementsystem.CourseService.util;

import java.util.UUID;

public class Util {

    public String generateId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
