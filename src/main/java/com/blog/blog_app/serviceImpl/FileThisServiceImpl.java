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

        return Random_GeneratedFull_FileName;
    }

    @Override
    public InputStream generatingFile(String path, String filename) throws FileNotFoundException {

        String FilePath  =  path+File.separator+filename;

        InputStream inputStream = new FileInputStream(FilePath);

        //db logic to return input stream

       return inputStream;
    }

    @Override
    public void deletingImage(String path, String filename){
        try {
            Files.deleteIfExists(Path.of(path, filename));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image.", e);
        }
    }

    @Override
    public String moveImage(String sourcePath, String destinationPath, String fileName) {


        try {

            Path source = Path.of(sourcePath, fileName);

            Path destination = Path.of(destinationPath, fileName);

            // Create destination folder if it doesn't exist
            Files.createDirectories(destination.getParent());

            // Move the file
            Files.move(source, destination);

            return fileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to move image.", e);
        }
    }


}
