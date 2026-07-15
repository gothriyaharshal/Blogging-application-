package com.blog.blog_app.repository;

import com.blog.blog_app.entity.Cateogary;
import com.blog.blog_app.entity.Post;
import com.blog.blog_app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    Page<Post> findByUser(User user,Pageable pageable); //here i want to gett all post by findByuserid here i passign userId and it give me post of it

    Page<Post> findByCateogary(Cateogary cateogary, Pageable pageable);

    List<Post> findByPostTitleContaining(String postTitle);



}
