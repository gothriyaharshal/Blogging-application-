package com.blog.blog_app.serviceImpl;

import com.blog.blog_app.services.FileServieForThisApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileThisServiceImpl implements FileServieForThisApplication {
    @Override
    public String creatingImage(String path, MultipartFile file) {

        //first i get the whole File name
        String Normal_name = file.getOriginalFilename();

        //generates its random file name
        String randomId = UUID.randomUUID().toString();

        String Random_GeneratedFull_FileName = randomId.concat(Normal_name.substring((Normal_name.lastIndexOf("."))));

        String fullPath = path + File.separator + Random_GeneratedFull_FileName;

        File file1 = new File(path);
        if (!(file1.exists())) {
            file1.mkdir();
        }

        try {
            Files.copy(file.getInputStream(), Path.of(fullPath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image.", e);
        }

        System.out.println(Random_GeneratedFull_FileName);

        return Normal_name;
    }

    @Override
    public InputStream generatingFile(String path, String filename) throws FileNotFoundException {

        String FilePath  =  path+File.separator+filename;

        InputStream inputStream = new FileInputStream(FilePath);

        //db logic to return input stream

       return inputStream;
    }
}
