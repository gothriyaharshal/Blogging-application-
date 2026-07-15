package com.blog.blog_app.repository;

import com.blog.blog_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User ,Integer>
{
    Optional<User> findByName(String name);

}
