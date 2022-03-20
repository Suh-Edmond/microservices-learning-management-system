package com.learningmanagementsystem.QuestionsAndAnswersService.proxy;

import com.learningmanagementsystem.QuestionsAndAnswersService.dto.UploadFileResponse;
import com.learningmanagementsystem.QuestionsAndAnswersService.model.FileCategory;
import com.learningmanagementsystem.QuestionsAndAnswersService.util.MessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "file-storage-service", url ="http://192.168.43.246:7000/api/v1/protected/" )
public interface FileStorageProxy {


    @PostMapping(path ="upload-files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> uploadFiles(@RequestParam("courseName") String courseName,
                                                       @RequestParam("fileCategory") FileCategory fileCategory,
                                                       @RequestPart(value = "file", required = true) MultipartFile multipartFile);
    @GetMapping("course-materials")
    public List<UploadFileResponse> getCourseMaterials(@RequestParam("courseName") String courseName,
                                                       @RequestParam("fileCategory") FileCategory fileCategory);

    @GetMapping("course-material")
    public UploadFileResponse getCourseMaterial(@RequestParam("courseName") String courseName,
                                                @RequestParam("fileCategory") FileCategory fileCategory,
                                                @RequestParam String fileName);

}
