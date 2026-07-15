package com.blog.blog_app.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    String updloadFile(String path , MultipartFile file) throws IOException;

    InputStream GeneratedFile(String path , String filename);

}
