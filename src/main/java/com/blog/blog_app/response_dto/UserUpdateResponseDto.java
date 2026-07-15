package com.blog.blog_app.response_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateResponseDto {

    private String name;

    private String email;

    private String about;


}
