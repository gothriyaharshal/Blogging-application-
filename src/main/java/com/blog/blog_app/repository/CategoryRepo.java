package com.blog.blog_app.repository;

import com.blog.blog_app.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

   boolean existsByCategoryTitle(String categoryTitle);
}
