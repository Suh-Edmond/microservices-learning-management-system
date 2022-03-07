package com.learningmanagementsystem.CourseService.service;

import com.learningmanagementsystem.CourseService.dto.FileCategory;
import com.learningmanagementsystem.CourseService.dto.UploadFileResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
    
    public void uploadFile(String courseName, FileCategory fileCategory, MultipartFile multipartFile);
    public UploadFileResponse getCourseMaterial(String courseName, FileCategory fileCategory, String fileName);
    public List<UploadFileResponse> getCourseMaterials(String courseName, FileCategory fileCategory);
}
