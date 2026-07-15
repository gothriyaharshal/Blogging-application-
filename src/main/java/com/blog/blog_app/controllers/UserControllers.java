package com.blog.blog_app.controllers;

import com.blog.blog_app.request_dto.UserCreatingDto;
import com.blog.blog_app.request_dto.UserDto;
import com.blog.blog_app.payloads.ApiResponse;
import com.blog.blog_app.response_dto.CreatingUserResponseDto;
import com.blog.blog_app.response_dto.UserUpdateResponseDto;
import com.blog.blog_app.response_dto.UserUpdateRoleResponseDto;
import com.blog.blog_app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserControllers {

    private final UserService userService;

    @Autowired
    UserControllers(UserService userService)
    {
        this.userService=userService;
    }


    @PostMapping("/create")
    public ResponseEntity<CreatingUserResponseDto> createUser(@Valid @RequestBody UserCreatingDto userDto) {
        CreatingUserResponseDto createdUser = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteMapping(@Valid @PathVariable Integer id) {
        this.userService.deleteUser(id);
        //we create an apiResponse class here for Responsing
        return new ResponseEntity<>(new ApiResponse("User Deleted Succesfully", true), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserUpdateResponseDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer id) {
      com.blog.blog_app.response_dto.UserUpdateResponseDto userUpdateResponseDto = this.userService.updateUser(userDto, id);
        return new ResponseEntity<>(userUpdateResponseDto, HttpStatus.OK);
    }


    //updating role by user id and role id
    @PutMapping("update/user/{userId}/role/{roleId}")
    public ResponseEntity<UserUpdateRoleResponseDto> updateRoleOfUser
    (
            @PathVariable Integer userId,
            @PathVariable Integer roleId
    )
    {
        UserUpdateRoleResponseDto userUpdateRoleResponseDto = this.userService.updateRoleOfUser(userId, roleId);
        return new ResponseEntity<>(userUpdateRoleResponseDto,HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CreatingUserResponseDto> getUserByID(@Valid @PathVariable Integer id) {
        CreatingUserResponseDto userById = this.userService.getUserById(id);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<CreatingUserResponseDto>> getAllUser() {
        List<CreatingUserResponseDto> allUsers = this.userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

}
