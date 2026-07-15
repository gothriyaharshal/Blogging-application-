package com.blog.blog_app.security;

import com.blog.blog_app.entity.User;
import com.blog.blog_app.exceptions.ResourceNotFoundException;
import com.blog.blog_app.repository.UserRepo;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    CustomUserDetailService(UserRepo userRepo)
    {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {

        User user = this.userRepo.findByName(username).orElseThrow(()->new ResourceNotFoundException("userName","name",username));

        return user;
    }
}
