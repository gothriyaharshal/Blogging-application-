package com.blog.blog_app.response_dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeneratingResponseForGetAllRole {
    private int roleId;
    private String roleName;
    private List<UserResponse> users;
}
