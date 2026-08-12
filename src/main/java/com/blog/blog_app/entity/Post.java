package com.blog.blog_app.entity;

import com.blog.blog_app.enums.PostStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
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

    @Size(max = 5000)
    private String postContent;

    private String imageName;

    private Date addedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    //here i did not took cascade because if i remove post 1 ..hibernate might be delete user related to it
    @JoinColumn()  //in post table joining and foreign key column name
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn() //in Category table joining and foreign key column name
    private Category category;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private List<Comment> postcommentList;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    private Integer professionalLinkCount;

    private LocalDateTime createdAt;

    private Integer amount;
    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        if (postStatus == null) {
            postStatus = PostStatus.PUBLISHED;
        }
        if (professionalLinkCount == null) {
            professionalLinkCount = 0;
        }
    }

    //one post have multiple payments
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Payment> payments;
}
