package com.blog.blog_app.request_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleRequestDto {

    private Integer roleId;
    private String rolename;
}
