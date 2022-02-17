package com.learningmanagementsystem.FileService.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileService {

    public void saveFile(String appName, MultipartFile file, String courseName, String fileCategory);
    public String getCourseImage(String appName, String courseName, String courseImageName, String fileCategory);
    public List<String> getCourseNotes(String appName, MultipartFile file, String courseName);
    public List<String> getCourseSyllabus(String appName, MultipartFile file, String courseName);
    public Resource loadFileAsResource(String appName, String courseName, String fileCategory, String fileName);
}
