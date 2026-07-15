package com.blog.blog_app.request_dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 4, max = 50, message = "UserName must be between 4 and 50 characters")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message="Your Email Address is not Valid!!")
    private String email;

    @JsonIgnore
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, max = 15, message = "Password must be minimum of 6 chars and maximum of 15 chars")
    private String password;

    @NotEmpty(message = "About cannot be empty")
    @Size(max = 200, message = "About cannot exceed 200 characters")
    private String about;
}
