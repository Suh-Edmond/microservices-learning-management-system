package com.learningmanagementsystem.QuestionsAndAnswersService.service.serviceImpl;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.CourseDto;
import com.learningmanagementsystem.QuestionsAndAnswersService.dto.UploadFileResponse;
import com.learningmanagementsystem.QuestionsAndAnswersService.exception.CustomIOException;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.FileCategory;
import com.learningmanagementsystem.QuestionsAndAnswersService.proxy.CourseProxy;
import com.learningmanagementsystem.QuestionsAndAnswersService.proxy.FileStorageProxy;
import com.learningmanagementsystem.QuestionsAndAnswersService.service.FileStorageService;
import com.learningmanagementsystem.QuestionsAndAnswersService.util.MessageResponse;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    FileStorageProxy fileStorageProxy;
    @Autowired
    CourseServiceImpl courseService;


    @Override
    public void uploadCourseFiles(String courseName, FileCategory fileCategory, MultipartFile file) {
        System.out.println(courseName);
        System.out.println(fileCategory);
        System.out.println(file);
        try {
          this.fileStorageProxy.uploadFiles(courseName, fileCategory, file);
        }catch (FeignException exception){
            throw exception;
        }
    }

    @Override
    public UploadFileResponse getCourseMaterial(String courseName, FileCategory fileCategory, String fileName) {
        UploadFileResponse response;
        try{
             response = this.fileStorageProxy.getCourseMaterial(courseName, fileCategory, fileName);
        }catch(FeignException feignException){
            response = null;
        }
        return response;
    }

    @Override
    public List<UploadFileResponse> getCourseMaterials(String courseName, FileCategory fileCategory) {
        List<UploadFileResponse> responses;
        try{
            responses = this.fileStorageProxy.getCourseMaterials(courseName, fileCategory);
        }catch(FeignException feignException){
            responses = null;
        }
        return responses;
    }
}
