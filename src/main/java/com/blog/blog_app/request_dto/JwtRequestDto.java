package com.blog.blog_app.request_dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequestDto {
    @NotBlank(message = "Name can be null")
    private String name;
    @NotBlank(message = "Password can't be null ")
    private String password;
}
