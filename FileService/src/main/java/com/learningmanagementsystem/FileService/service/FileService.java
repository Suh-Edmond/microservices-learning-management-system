package com.learningmanagementsystem.FileService.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileService {

    public void saveFile(String appName, MultipartFile file, String courseName, String fileCategory);
    public String getCourseImage(String courseImageName);
    public List<File> getCourseNotes(String appName, MultipartFile file, String courseName);
    public List<File> getCourseSyllabus(String appName, MultipartFile file, String courseName);
    public Resource loadFileAsResource(String appName, String courseName, String fileCategory, String fileName);
}
