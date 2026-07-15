package com.blog.blog_app.services;

import com.blog.blog_app.request_dto.CreatingPostDto;
import com.blog.blog_app.request_dto.PostDto;
import com.blog.blog_app.response_dto.CreatedPostResponse;
import com.blog.blog_app.response_dto.PostCateogaryResponse;
import com.blog.blog_app.response_dto.PostResponse;
import com.blog.blog_app.response_dto.PostResponseByUerId;

import java.util.List;

public interface PostService {

    CreatedPostResponse createPost(CreatingPostDto creatingPostDto , Integer userId , Integer cateogaryId);

    PostDto updatePost(PostDto postDto, Integer postid);

    PostDto getPostByID(Integer postId);

    List<PostDto> getAllPost();

    void deletePost(Integer postId);

    PostResponseByUerId getPostByUserId(Integer user, Integer PageNumber, Integer PageSize,String sortBy,String sortDir);

    PostCateogaryResponse getPostByCateogaryId(Integer cateogory, Integer PageNumber, Integer PageSize,String sortBy,String sortDir);

    List<PostDto> searchPost(String KeyWord);


    //getting allo post by applying pagination and sorting our there
    PostResponse getAllPostInPaginationAndSorting(Integer PageNumber , Integer PageSize ,String sortBy, String sortDir);




}
