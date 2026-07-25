package com.blog.blog_app.request_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCateogaryDtoRequest {

    @NotBlank(message = "Please Define Which type of Category post is this")
    @Size(min = 3, max = 500, message = "Minimun size is 3 and maximum size is 50")
    private String categoryTitle;


    @NotBlank(message = "Please provide Category description")
    @Size(min = 3, max = 500, message = "Minimun size is 3 and maximum size is 50")
    private String categoryDescription;
}


