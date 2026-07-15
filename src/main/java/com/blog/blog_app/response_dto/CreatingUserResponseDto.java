package com.blog.blog_app.response_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatingUserResponseDto {

    private Integer id;
    private String name;
    private String email;
    private String about;
    private List<RoleResponse> role;

}
