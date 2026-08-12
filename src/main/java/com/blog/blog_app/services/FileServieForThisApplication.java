package com.blog.blog_app.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface FileServieForThisApplication {


    //first we setting image location here with file name and path name

    String creatingImage(String path , MultipartFile file);

    InputStream generatingFile(String path , String filename) throws FileNotFoundException;


    //deleting files
    public void deletingImage(String path , String filename) throws FileNotFoundException;

    //moving file from one folder to another folder
    String moveImage(String sourcePath, String destinationPath, String fileName);


}
