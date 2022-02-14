package com.learningmanagementsystem.FileService.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileService {

    public void saveFile(String appName, MultipartFile file, String courseName, String fileCategory);
    public void getCourseImage();
    public List<File> getCourseNotes(String appName, MultipartFile file, String courseName, String fileCategory);

}
