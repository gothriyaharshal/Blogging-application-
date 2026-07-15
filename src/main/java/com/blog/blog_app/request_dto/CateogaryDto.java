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
public class CateogaryDto {

    private Integer cateogary_id;

    @NotBlank(message = "Please Define Which type of Cateogary post is this")
    @Size(min = 3,max = 50 , message = "Minimun size is 3 and maximum size is 50")
    private String cateogary_title;

    @NotBlank(message = "Please provide Cateogary description")
    @Size(min = 3 , max = 50,message = "Minimun size is 3 and maximum size is 50")
    private String cateogary_Description;
}
