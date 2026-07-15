package com.blog.blog_app.controllers;

import com.blog.blog_app.request_dto.CommentDto;
import com.blog.blog_app.response_dto.CreatedCommentResponse;
import com.blog.blog_app.response_dto.DeletedCommentResponse;
import com.blog.blog_app.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {


    private final CommentService commentService;

    @Autowired
    CommentController(CommentService commentService)
    {
        this.commentService = commentService;
    }

    //creating comment

    @PostMapping("/{userId}/{postId}/create")
    public ResponseEntity<CreatedCommentResponse> creatingComment
            (@RequestBody CommentDto commentDto
                    , @PathVariable Integer userId
                    , @PathVariable Integer postId
            ) {
        CreatedCommentResponse comment = this.commentService.createComment(userId, postId, commentDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }


    @PutMapping("/update/{commentId}/post/{postId}/user/{userId}")
    public ResponseEntity<CreatedCommentResponse> updatingCommnet
            (
                    @PathVariable Integer commentId,
                    @PathVariable Integer postId,
                    @PathVariable Integer userId,
                    @RequestBody CommentDto commentDto
            ) {
        CreatedCommentResponse c = this.commentService.updatedComment(commentId, commentDto, userId, postId);

        return new ResponseEntity<>(c, HttpStatus.OK);
    }


    @GetMapping("/getTing/{commentID}")
    public ResponseEntity<CreatedCommentResponse> getCommentById
            (@PathVariable Integer commentID)
    {
        CreatedCommentResponse createdCommentResponseLikeByWhichuserAndOneWhichPost = this.commentService.gettingsingleCommentByCommentID(commentID);

      return new ResponseEntity<>(createdCommentResponseLikeByWhichuserAndOneWhichPost,HttpStatus.OK);
    }



    @GetMapping("/deleting/{commentID}")
    public ResponseEntity<DeletedCommentResponse> deletingByCommentID(@PathVariable Integer commentID)
    {
        DeletedCommentResponse deletedCommentResponse = this.commentService.deletingCommentID(commentID);
        return new ResponseEntity<>(deletedCommentResponse,HttpStatus.OK);
    }


    @GetMapping("/gettingAll")
    public ResponseEntity<List<CreatedCommentResponse>> gettingAllComments()
    {
        List<CreatedCommentResponse> createdCommentResponseLikeByWhichuserAndOneWhichPosts = this.commentService.gettingAllComment();
        return new ResponseEntity<>(createdCommentResponseLikeByWhichuserAndOneWhichPosts,HttpStatus.OK);
    }


}
