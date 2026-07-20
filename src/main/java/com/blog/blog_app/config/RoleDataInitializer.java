package com.blog.blog_app.config;

import com.blog.blog_app.entity.Role;
import com.blog.blog_app.repository.RoleRepo;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleDataInitializer implements CommandLineRunner {

    private final RoleRepo roleRepo;

    @Autowired
    RoleDataInitializer(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public void run(String @NonNull ... args) {
        if (roleRepo.count() == 0) {
            this.roleRepo.save(new Role("ROLE_ADMIN"));
            this.roleRepo.save(new Role("ROLE_USER"));
        }
    }
}
