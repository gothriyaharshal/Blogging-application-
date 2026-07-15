package com.blog.blog_app.controllers;

import com.blog.blog_app.response_dto.GeneratingResponseForGetAllRole;
import com.blog.blog_app.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/getRole")
public class GetAllUserRoleController {


    private final RoleService roleService;

    @Autowired
    GetAllUserRoleController(RoleService roleService)
    {
        this.roleService=roleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<GeneratingResponseForGetAllRole>> gettingAllRole()
    {
        List<GeneratingResponseForGetAllRole> generatingResponseForGetAllRoles = this.roleService.gettingAllrole();
        return new ResponseEntity<>(generatingResponseForGetAllRoles, HttpStatus.OK);
    }

}
