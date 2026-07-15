package com.blog.blog_app.services;

import com.blog.blog_app.request_dto.CommentDto;
import com.blog.blog_app.response_dto.CreatedCommentResponse;
import com.blog.blog_app.response_dto.DeletedCommentResponse;

import java.util.List;

public interface CommentService {

    CreatedCommentResponse createComment(Integer userId, Integer postID, CommentDto commentDto);


    //for updating an comment we only need comment it because it is uniuqe
    CreatedCommentResponse updatedComment(Integer commentId , CommentDto commentDto, Integer userId , Integer postId);

    //for deleting comment id that time i also want .....comment id
    DeletedCommentResponse deletingCommentID(Integer commentId);

    //for getting comment by Comment id
    CreatedCommentResponse gettingsingleCommentByCommentID(Integer commentId);

    //getting all comment
    List<CreatedCommentResponse> gettingAllComment();

}
