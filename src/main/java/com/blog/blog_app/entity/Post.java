package com.blog.blog_app.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    private String postTitle;

    private String postContent;

    private String imageName;

    private Date addedDate;

    @ManyToOne(fetch = FetchType.LAZY)  //here i did not took cascade because if i remove post 1 ..hibernate might be delete user related to it
    @JoinColumn()  //in post table joining and foreign key column name
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn() //in Cateogary table joining and foreign key column name
    private Cateogary cateogary;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER )
    private List<Comment> postcommentList;

}
