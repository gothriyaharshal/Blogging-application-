package com.blog.blog_app.serviceImpl;

import com.blog.blog_app.entity.Comment;
import com.blog.blog_app.entity.Post;
import com.blog.blog_app.entity.User;
import com.blog.blog_app.exceptions.ResourceNotFoundException;
import com.blog.blog_app.repository.CommentRepo;
import com.blog.blog_app.repository.PostRepo;
import com.blog.blog_app.repository.UserRepo;
import com.blog.blog_app.request_dto.CommentDto;
import com.blog.blog_app.response_dto.CreatedCommentResponse;
import com.blog.blog_app.response_dto.DeletedCommentResponse;
import com.blog.blog_app.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    private final UserRepo userRepo;
    private final PostRepo postRepo;
    private final CommentRepo commentRepo;
    private final ModelMapper modelMapper;


    @Autowired
    public CommentServiceImpl(UserRepo userRepo,
                              PostRepo postRepo,
                              CommentRepo commentRepo,
                              ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CreatedCommentResponse createComment(Integer userId, Integer postID, CommentDto commentDto) {

        //first of all i validating user
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

        //validating on which post user want to comment
        Post post = this.postRepo.findById(postID).orElseThrow(() -> new ResourceNotFoundException("post", "id", postID));

        //converting comment Dto into Comment and then save comment into db
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = this.commentRepo.save(comment);


        //converting Saved comment in Response Comment
        CreatedCommentResponse createdCommentResponse = new CreatedCommentResponse();
        createdCommentResponse.setUserId(userId);
        createdCommentResponse.setPostId(postID);
        createdCommentResponse.setComment(savedComment.getComment());

        return createdCommentResponse;
    }

    @Override
    public CreatedCommentResponse updatedComment(Integer commentId, CommentDto commentDto, Integer userId, Integer postId) {


        //first of all i validating user
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

        //validating on which post user want to comment
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));


        //validating comment id first
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        comment.setComment(commentDto.getComment());
        comment.setUser(user);
        comment.setPost(post);


        this.commentRepo.save(comment);

        //then we map comment into CreatedcommentResponse one
        CreatedCommentResponse createdCommentResponse = new CreatedCommentResponse();
        createdCommentResponse.setComment(comment.getComment());
        createdCommentResponse.setPostId(postId);
        createdCommentResponse.setUserId(userId);


        return createdCommentResponse;
    }


    @Override
    public CreatedCommentResponse gettingsingleCommentByCommentID(Integer commentId) {

        //validating comment
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

        CreatedCommentResponse c = new CreatedCommentResponse();
        c.setCommentId(comment.getCommentId());
        c.setComment(comment.getComment());

        return c;

    }


    @Override
    public DeletedCommentResponse deletingCommentID(Integer commentId) {
        //validating comment
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        this.commentRepo.delete(comment);

        return new DeletedCommentResponse("Comment deleted succesFully");
    }

    @Override
    public List<CreatedCommentResponse> gettingAllComment() {

        List<Comment> comment = this.commentRepo.findAll();
        return comment.stream().map(comment1 -> this.modelMapper.map(comment1, CreatedCommentResponse.class)).collect(Collectors.toList());
    }
}
