package com.learningmanagementsystem.CourseService.service.serviceImpl;

import com.learningmanagementsystem.CourseService.dto.FileCategory;
import com.learningmanagementsystem.CourseService.dto.UploadFileResponse;
import com.learningmanagementsystem.CourseService.exception.CustomIOException;
import com.learningmanagementsystem.CourseService.proxy.FileStorageProxy;
import com.learningmanagementsystem.CourseService.service.FileStorageService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileStorageServiceImpl  implements FileStorageService {

    @Autowired
    FileStorageProxy fileStorageProxy;

    @Override
    public void uploadFile(String courseName, FileCategory fileCategory, MultipartFile multipartFile) {

        try {
            this.fileStorageProxy.uploadFiles(courseName, fileCategory, multipartFile);
        }catch (FeignException exception){
            throw new CustomIOException("Error Occurred! Could not upload file");
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
        List<UploadFileResponse> uploadFileResponseList;
        try{
            uploadFileResponseList = this.fileStorageProxy.getCourseMaterials(courseName, fileCategory);
        }catch(FeignException feignException){
            uploadFileResponseList = null;
        }
        return uploadFileResponseList;
    }
}
