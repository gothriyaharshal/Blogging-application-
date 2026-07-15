package com.blog.blog_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    private String comment;

    //user is comenting one user can do multiple comments
    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;


    //one post has multiple comments
    @ManyToOne
    @JoinColumn(name = "postID")
    private Post post;
}
