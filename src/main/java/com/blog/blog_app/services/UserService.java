package com.blog.blog_app.services;

import com.blog.blog_app.request_dto.UserCreatingDto;
import com.blog.blog_app.request_dto.UserDto;
import com.blog.blog_app.response_dto.CreatingUserResponseDto;
import com.blog.blog_app.response_dto.UserUpdateResponseDto;
import com.blog.blog_app.response_dto.UserUpdateRoleResponseDto;

import java.util.List;

public interface UserService {

    CreatingUserResponseDto createUser(UserCreatingDto userDto);

    UserUpdateResponseDto updateUser(UserDto userDto, Integer userId);

    UserUpdateRoleResponseDto updateRoleOfUser(Integer userId , Integer roleId);

    CreatingUserResponseDto getUserById(Integer userID);

    List<CreatingUserResponseDto> getAllUsers();

    void deleteUser(Integer userID);

}
