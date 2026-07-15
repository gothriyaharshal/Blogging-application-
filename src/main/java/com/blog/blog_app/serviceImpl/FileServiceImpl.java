package com.blog.blog_app.serviceImpl;

import com.blog.blog_app.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String updloadFile(String path, MultipartFile file) throws IOException {


        //first of all i get the file name
        String name = file.getOriginalFilename();

//we generate random name of image not giving exact name at file path

        //getting random id for storing file because there we store multiple same image ...
        String randomID= UUID.randomUUID().toString();
        //suppose our file name is abc.png then we took out png part only
     String FilePathRandomName =   randomID.concat(name.substring((name.lastIndexOf("."))));
//we took out .png part form file name and in front of that png we apply random id


        //Full path of file wehere i want to store this
        String FilePath = path+ File.separator+FilePathRandomName;

        //then i check is there any location in project if not then make
        File f = new File(path);
        if(!f.exists())
        {
            f.mkdir();
        }

        //then i save my file full path +file into this (f)path
        Files.copy(file.getInputStream(), Paths.get(FilePath));

        return name;

    }

    @Override
    public InputStream GeneratedFile(String path, String filename) {

        String FullPath = path+File.separator+filename;

        try {
            InputStream inputStream = new FileInputStream(FullPath);

            return inputStream;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
