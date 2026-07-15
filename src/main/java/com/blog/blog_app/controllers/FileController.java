package com.blog.blog_app.controllers;

import com.blog.blog_app.response_dto.FileResponse;
import com.blog.blog_app.services.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/uploading")
public class FileController {


    private final FileService fileService;

    @Autowired
    FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Value("${project.image}")
    private String path;

    @PostMapping("/image")
    public ResponseEntity<FileResponse> uploadingFile(

            @RequestParam("image") MultipartFile image

    ) {
        String name = null;
        try {

            name = fileService.updloadFile(path, image);

        } catch (IOException e) {

            return new ResponseEntity<>(new FileResponse(name, "File not uploaded"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new FileResponse(name, "File  uploaded  Succesfully"), HttpStatus.OK);

    }


    @GetMapping("/profile/{imagename}")
    public void GeneratingFile(
            @PathVariable String imagename,
            HttpServletResponse httpResponse

    ) throws IOException {
        InputStream stream = this.fileService.GeneratedFile(path, imagename);
        httpResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(stream, httpResponse.getOutputStream());
    }

}
