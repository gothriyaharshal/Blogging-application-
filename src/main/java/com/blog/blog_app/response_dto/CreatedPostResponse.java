package com.blog.blog_app.response_dto;

import com.blog.blog_app.request_dto.CateogaryDto;
import com.blog.blog_app.request_dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatedPostResponse {

    private Integer postId;
    private String postTitle;
    private String postContent;
    private Date addedDate;
    private String imageName;
    private UserDto user;
    private CateogaryDto cateogary;
}


