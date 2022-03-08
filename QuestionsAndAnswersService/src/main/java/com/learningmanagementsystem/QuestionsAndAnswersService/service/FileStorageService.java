package com.learningmanagementsystem.QuestionsAndAnswersService.service;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.UploadFileResponse;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.FileCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
    public void uploadCourseFiles(String courseName, FileCategory fileCategory, MultipartFile file);
    public UploadFileResponse getCourseImage(String courseName, FileCategory fileCategory, String fileName);
    public List<UploadFileResponse> getCourseMaterials(String courseName, FileCategory fileCategory);
}
