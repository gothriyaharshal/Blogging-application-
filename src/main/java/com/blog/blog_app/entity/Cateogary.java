package com.blog.blog_app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Cateogary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cateogary_id;
    private String cateogary_title;
    private String cateogary_Description;

    @OneToMany(mappedBy = "cateogary" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Post> posts;
}
