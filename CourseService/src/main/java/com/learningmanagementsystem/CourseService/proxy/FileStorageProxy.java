package com.learningmanagementsystem.CourseService.proxy;


import com.learningmanagementsystem.CourseService.dto.FileCategory;
import com.learningmanagementsystem.CourseService.dto.UploadFileResponse;
import com.learningmanagementsystem.CourseService.util.MessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "file-storage-service",  url="http://localhost:7000/api/v1/protected/")
public interface FileStorageProxy {

    @PostMapping(path ="upload-files" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MessageResponse uploadFiles(@RequestParam("courseName") String courseName,
                                       @RequestParam("fileCategory") FileCategory fileCategory,
                                       @RequestPart("file") MultipartFile multipartFile);
    @GetMapping("course-materials")
    public List<UploadFileResponse> getCourseMaterials(@RequestParam("courseName") String courseName,
                                                       @RequestParam("fileCategory") FileCategory fileCategory);

    @GetMapping("course-material")
    public UploadFileResponse getCourseMaterial(@RequestParam("courseName") String courseName,
                                                                @RequestParam("fileCategory") FileCategory fileCategory,
                                                                @RequestParam String fileName);

}
