package com.blog.blog_app.request_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserDto {

        private Integer id;

        @NotEmpty(message = "Name cannot be empty")
        @Size(min = 4, max = 50, message = "UserName must be between 4 and 50 characters")
        private String name;

        @NotEmpty(message = "Email cannot be empty")
        @Email(message="Your Email Address is not Valid!!")
        private String email;


        @NotEmpty(message = "Password cannot be empty")
        @Size(min = 3, max = 15, message = "Password must be minimum of 6 chars and maximum of 15 chars")
        private String password;

        @NotEmpty(message = "About cannot be empty")
        @Size(max = 200, message = "About cannot exceed 200 characters")
        private String about;

}
