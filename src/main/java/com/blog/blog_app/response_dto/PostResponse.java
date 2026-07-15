package com.blog.blog_app.response_dto;

import com.blog.blog_app.request_dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private List<PostDto> allPost;
    private int pageNumber;
    private int pageSize;
    private int totalElement;
    private int totalPages;
    private boolean lastPage;
    private int UserID;
    private int cateogaryId;

}
