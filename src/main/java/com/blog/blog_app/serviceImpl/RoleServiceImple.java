package com.blog.blog_app.serviceImpl;

import com.blog.blog_app.entity.Role;
import com.blog.blog_app.repository.RoleRepo;
import com.blog.blog_app.response_dto.GeneratingResponseForGetAllRole;
import com.blog.blog_app.response_dto.UserResponse;
import com.blog.blog_app.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImple implements RoleService {

    private final RoleRepo roleRepo;

    @Autowired
    RoleServiceImple(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }


    @Override
    public List<GeneratingResponseForGetAllRole> gettingAllrole() {

        List<Role> all = this.roleRepo.findAll();

        List<GeneratingResponseForGetAllRole> response = new ArrayList<>();

        for (Role a : all) {
            GeneratingResponseForGetAllRole generatingResponseForGetAllRole = new GeneratingResponseForGetAllRole();
            generatingResponseForGetAllRole.setRoleId(a.getRoleId());
            generatingResponseForGetAllRole.setRoleName(a.getRoleName());

            List<UserResponse> userResponses = a.getUser().stream().map(user ->
            {
                UserResponse userResponse = new UserResponse();
                userResponse.setId(user.getId());
                userResponse.setName(user.getName());
                return userResponse;
            }).collect(Collectors.toList());

            generatingResponseForGetAllRole.setUsers(userResponses);

            response.add(generatingResponseForGetAllRole);
        }

        return response;
    }
}
