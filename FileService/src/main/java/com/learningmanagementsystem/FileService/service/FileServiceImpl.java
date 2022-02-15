package com.learningmanagementsystem.FileService.service;

import com.learningmanagementsystem.FileService.exception.CustomIOException;
import com.learningmanagementsystem.FileService.exception.FileStorageException;
import com.learningmanagementsystem.FileService.exception.ResourceNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileServiceImpl implements  FileService{

    private final String baseDir = "uploads";

    @Override
    public void saveFile(String appName, MultipartFile file, String courseName, String fileCategory) {
        String  finalCourseName = courseName.replaceAll(" ", "");
        String finalAppName = appName.replaceAll(" ", "");
        Path courseUploadDir = Paths.get(this.baseDir + "/" + fileCategory + "/" + finalAppName + "/" + finalCourseName ).normalize();
        if(!Files.exists(courseUploadDir)){
            try {
                Files.createDirectories(courseUploadDir);
            }catch (IOException e) {
                throw  new CustomIOException("could not create folder to store the file");
            }
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains("..")){
            throw new FileStorageException("File contains invalid character");
        }
        Path targetLocation = courseUploadDir.resolve(fileName);
        try {
           Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
           throw new FileStorageException("Could not upload file");
        }
    }

    @Override
    public String getCourseImage(String courseImageName) {
        String courseImage = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/protected/downloadFile/").path(courseImageName).toUriString();
        return courseImage;
    }


    @Override
    public List<File> getCourseNotes(String appName, MultipartFile file, String courseName) {
        return null;
    }

    @Override
    public List<File> getCourseSyllabus(String appName, MultipartFile file, String courseName) {
        return null;
    }


    @Override
    public Resource loadFileAsResource(String appName, String courseName, String fileCategory, String fileName) {

        Path path = Paths.get(this.baseDir + "/" + fileCategory + "/" + appName + "/" + courseName).
                resolve(fileName).normalize();
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new FileStorageException("File path not found");
        }
        if(resource.exists()){
                return resource;
            }else {
                throw new ResourceNotFoundException("File not found"+ fileName);
            }

    }
}
