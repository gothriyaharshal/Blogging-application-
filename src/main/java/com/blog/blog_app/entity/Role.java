package com.blog.blog_app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(unique = true, nullable = false)
    private String roleName;

   @ManyToMany(fetch = FetchType.EAGER, mappedBy = "role")
    private Set<User> user = new HashSet<>();


    public Role(String roleName) {
        this.roleName = roleName;
    }
}
