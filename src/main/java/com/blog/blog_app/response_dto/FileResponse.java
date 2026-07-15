package com.blog.blog_app.response_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileResponse {
    private String FileName;
    private String message;
}
