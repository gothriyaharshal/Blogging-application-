package com.blog.blog_app.request_dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatingPostDto {


    @NotBlank(message = "Please Provide Post title")
    @Size(min = 5, max = 100, message = "Title length must between 3 to 100")
    private String postTitle;

    @NotBlank(message = "Please Provide Post Content")
    @Size(min = 5, max = 100, message = "Content length must between 3 to 100")
    private String postContent;


    // private String imageName;  here i did not took image name i store it into local

    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss a")
    @NotNull(message = "Provide Date in Date Time Formate....ex= yyyy/MM/dd hh:mm:ss a  (2026/07/07 03:45:28 PM)")
    private Date addedDate;

    private String imageName = "Harshal.png";

}

