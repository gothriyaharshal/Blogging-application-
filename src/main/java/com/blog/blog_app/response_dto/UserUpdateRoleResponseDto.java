package com.blog.blog_app.response_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRoleResponseDto {

    private String name;
    private String email;
    private String about;
    private List<RoleResponse> role;

}
