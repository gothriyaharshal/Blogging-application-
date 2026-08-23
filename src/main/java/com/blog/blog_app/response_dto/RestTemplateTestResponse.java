package com.blog.blog_app.response_dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestTemplateTestResponse {
    private Integer userId;
    private Integer id;
    private String title;
    private String body;

    @Override
    public String toString() {
        return userId + ":" + id + ":" + title + ":" + body;
    }
}
