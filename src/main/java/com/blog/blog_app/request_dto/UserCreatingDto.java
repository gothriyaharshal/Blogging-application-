package com.blog.blog_app.request_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatingDto {

   @NotBlank(message = "Name cannot be blank")
   @Size(min = 3, max = 30 , message = "user name must be greater then 2 character")
   private String name;

   @NotBlank(message = "Email cannot be empty")
   @Email(message = "Please enter a valid email address")
   private String email;

   
   @NotBlank(message = "Please provide about")
   private String about;

   @NotBlank(message = "Password is neccessary")
   private String password;

}
