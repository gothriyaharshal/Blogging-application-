package com.blog.blog_app.repository;

import com.blog.blog_app.entity.Cateogary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Cateogary, Integer> {
}
