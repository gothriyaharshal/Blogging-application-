package com.blog.blog_app.services;

import com.blog.blog_app.enums.VerificationResult;
import com.blog.blog_app.request_dto.CreatingPostDto;
import com.blog.blog_app.request_dto.PostDto;
import com.blog.blog_app.response_dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PendingPostResponse createPost(CreatingPostDto creatingPostDto , Integer userId , Integer categoryId);

    VerificationResult verifyPostContent(Integer postId) ;

        CreatedPostResponse updatePost(CreatingPostDto postDto, Integer posted);

    CreatedPostResponse getPostByID(Integer postId);

    List<PostDto> getAllPost();

    void deletePost(Integer postId);

    PostResponseByUerId getPostByUserId(Integer user, Integer PageNumber, Integer PageSize,String sortBy,String sortDir);

    PostCateogaryResponse getPostByCategoryId(Integer cateogory, Integer PageNumber, Integer PageSize, String sortBy, String sortDir);

    List<PostDto> searchPost(String KeyWord);


    //getting allo post by applying pagination and sorting our there
    PostResponse getAllPostInPaginationAndSorting(Integer PageNumber , Integer PageSize ,String sortBy, String sortDir);


}
