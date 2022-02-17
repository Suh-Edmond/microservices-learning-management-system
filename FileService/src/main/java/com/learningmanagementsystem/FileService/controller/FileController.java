package com.learningmanagementsystem.FileService.controller;


import com.learningmanagementsystem.FileService.exception.FileStorageException;
import com.learningmanagementsystem.FileService.model.FileCategory;
import com.learningmanagementsystem.FileService.model.MessageResponse;
import com.learningmanagementsystem.FileService.service.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/protected/")
public class FileController {

    @Autowired
    private FileServiceImpl fileService;

    @PostMapping(path ="upload-files")
    public ResponseEntity<MessageResponse> uploadFiles( @RequestParam("appName") String appName,
                                                           @RequestParam("courseName") String courseName,
                                                           @RequestParam("fileType") FileCategory fileCategory,
                                                           @RequestPart(value = "file", required = true) MultipartFile multipartFile){
        this.fileService.saveFile(appName, multipartFile, courseName, fileCategory.toString());
        return new ResponseEntity<>(new MessageResponse("success", "File uploaded successfully",new Date()), HttpStatus.OK);
    }

    @GetMapping(path = "downloadFile")
    public ResponseEntity<?> downloadFile(@RequestParam("fileName") String fileName, @RequestParam("appName") String appName,
                                          @RequestParam("courseName") String courseName,
                                          @RequestParam("fileType") FileCategory fileCategory, HttpServletRequest httpServletRequest){
        Resource resource = this.fileService.loadFileAsResource(appName, courseName, fileCategory.toString(), fileName);
        String contentType = null;
        try {
            contentType = httpServletRequest.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
           throw new FileStorageException("File content not error");
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ resource.getFilename() + "\"").body(resource);
    }

    @GetMapping("course-images")
    public ResponseEntity<MessageResponse> getCourseImage(@RequestParam("appName") String appName,
                                                            @RequestParam("courseName") String courseName,
                                                            @RequestParam("fileType") FileCategory fileCategory,
                                                          @RequestParam String fileName){
        String courseImage = this.fileService.getCourseImage(appName, courseName, fileName, fileCategory.toString());
        return new ResponseEntity<>(new MessageResponse("success", courseImage, new Date()), HttpStatus.OK);
    }



}
