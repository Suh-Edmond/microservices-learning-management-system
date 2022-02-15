package com.learningmanagementsystem.FileService.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class FileServiceImpl implements  FileService{
    @Override
    public void saveFile(String appName, MultipartFile file, String courseName, String fileCategory) {

    }

    @Override
    public void getCourseImage() {

    }

    @Override
    public List<File> getCourseNotes(String appName, MultipartFile file, String courseName, String fileCategory) {
        return null;
    }
}
