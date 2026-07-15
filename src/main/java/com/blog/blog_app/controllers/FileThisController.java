package com.blog.blog_app.controllers;

import com.blog.blog_app.response_dto.FileResponse;
import com.blog.blog_app.services.FileServieForThisApplication;
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
@RequestMapping("/files")
public class FileThisController {


    private final FileServieForThisApplication fileServieForThisApplication;


    @Autowired
    FileThisController(FileServieForThisApplication fileServieForThisApplication) {
        this.fileServieForThisApplication = fileServieForThisApplication;
    }


    @Value("${project.ThisImageApp}")
    private String path;

    @PostMapping("/uplaodingFile")
    public ResponseEntity<FileResponse> StoringFileToServerLoacally(

            @RequestParam("imagename") MultipartFile imagename
    ) {

        String name = null;

        try {
            name = this.fileServieForThisApplication.creatingImage(path, imagename);
        } catch (Exception e) {

            return new ResponseEntity<>(new FileResponse(name, "Some error occured"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new FileResponse(name, "File uploaded succesfully"), HttpStatus.OK);

    }


    @GetMapping("/getting/{imagename}")
    public void GeneratedServingFile(

            @PathVariable String imagename,
            HttpServletResponse httpServletResponse
    ) throws IOException {
        InputStream inputStream = this.fileServieForThisApplication.generatingFile(path, imagename);
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(inputStream, httpServletResponse.getOutputStream());
    }

}
