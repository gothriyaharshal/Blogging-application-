package com.blog.blog_app.response_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CreatedCommentResponse {

    private Integer commentId;
    private String comment;
    private Integer userId;
    private Integer postId;

}
