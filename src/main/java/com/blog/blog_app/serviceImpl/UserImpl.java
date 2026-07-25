package com.blog.blog_app.serviceImpl;

import com.blog.blog_app.entity.Role;
import com.blog.blog_app.exceptions.DuplicateEntryException;
import com.blog.blog_app.payloads.AppConstants;
import com.blog.blog_app.repository.RoleRepo;
import com.blog.blog_app.request_dto.UserCreatingDto;
import com.blog.blog_app.request_dto.UserDto;
import com.blog.blog_app.entity.User;
import com.blog.blog_app.exceptions.ResourceNotFoundException;
import com.blog.blog_app.repository.UserRepo;
import com.blog.blog_app.response_dto.CreatingUserResponseDto;
import com.blog.blog_app.response_dto.RoleResponse;
import com.blog.blog_app.response_dto.UserUpdateResponseDto;
import com.blog.blog_app.response_dto.UserUpdateRoleResponseDto;
import com.blog.blog_app.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserImpl implements UserService {

    private final UserRepo userRepo;


    private final ModelMapper modelMapper;


    private final PasswordEncoder passwordEncoder;

    private final RoleRepo roleRepo;

    @Autowired
    UserImpl(UserRepo userRepo, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }


    @Override
    public CreatingUserResponseDto createUser(UserCreatingDto userDto) {

        if(this.userRepo.existsByEmail(userDto.getEmail()))
        {
            throw  new DuplicateEntryException("user","email", userDto.getEmail());
        }


        //mapping up userCreatingDto to user
        User user = this.modelMapper.map(userDto, User.class);

        //do manually set up of password and role
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        //setting up role also
        Role role = this.roleRepo.findById(AppConstants.NORMAL_ROLE_ID).orElseThrow(() -> new ResourceNotFoundException("roleID", "id", AppConstants.NORMAL_ROLE_ID));
        //i gett the role which were in list formate then i set them in user
        user.getRole().add(role);

        //save user into db
        User savedUser = this.userRepo.save(user);


        CreatingUserResponseDto creatingUserResponseDto = this.modelMapper.map(savedUser, CreatingUserResponseDto.class);
        List<RoleResponse> collect = savedUser.getRole().stream().map(role1 ->
        {
            RoleResponse rolerespone = new RoleResponse();
            rolerespone.setRoleId(role1.getRoleId());
            rolerespone.setRoleName(role1.getRoleName());
            return rolerespone;
        }).collect(Collectors.toList());

        creatingUserResponseDto.setRole(collect);

        return creatingUserResponseDto;
    }


    @Override
    public UserUpdateResponseDto updateUser(UserDto userDto, Integer userId) {

        User validatingUser = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        User user = this.modelMapper.map(userDto, User.class);
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        User save = this.userRepo.save(user);
        return this.modelMapper.map(save, UserUpdateResponseDto.class);
    }

    @Override
    public UserUpdateRoleResponseDto updateRoleOfUser(Integer userId, Integer roleId) {

        //validating user id
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

        //validating role id
        Role role = this.roleRepo.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("role", "id", roleId));

        //adding role if i want to remove previous role and set up new role the i run command ...user.getRole.remove and then add
        user.getRole().add(role);

        User savedUser = this.userRepo.save(user);

        UserUpdateRoleResponseDto userUpdateRoleResponseDto = this.modelMapper.map(user, UserUpdateRoleResponseDto.class);
        //now setting up role in userRupateRoleResponse
        List<RoleResponse> collect = savedUser.getRole().stream().map(role1 ->
                {
                    RoleResponse roleResponse = new RoleResponse();
                    roleResponse.setRoleId(role1.getRoleId());
                    roleResponse.setRoleName(role1.getRoleName());
                    return roleResponse;
                }
        ).collect(Collectors.toList());

        userUpdateRoleResponseDto.setRole(collect);

        return userUpdateRoleResponseDto;
    }


    @Override
    public CreatingUserResponseDto getUserById(Integer userID) {

        User savedUser = this.userRepo.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));

        CreatingUserResponseDto creatingUserResponseDto = this.modelMapper.map(savedUser, CreatingUserResponseDto.class);
        List<RoleResponse> collect = savedUser.getRole().stream().map(role1 ->
        {
            RoleResponse rolerespone = new RoleResponse();
            rolerespone.setRoleId(role1.getRoleId());
            rolerespone.setRoleName(role1.getRoleName());
            return rolerespone;
        }).collect(Collectors.toList());

        creatingUserResponseDto.setRole(collect);

        return creatingUserResponseDto;
    }


    @Override
    public List<CreatingUserResponseDto> getAllUsers() {

        List<User> users = this.userRepo.findAll();

        List<CreatingUserResponseDto> response = new ArrayList<>();

        for (User user : users) {
         CreatingUserResponseDto creatingUserResponseDto =   this.modelMapper.map(user, CreatingUserResponseDto.class);
            //for setting up role we also do it manually
            List<RoleResponse> collects = user.getRole().stream().map(role ->
            {
                RoleResponse roleResponse = new RoleResponse();
                roleResponse.setRoleName(role.getRoleName());
                roleResponse.setRoleId(role.getRoleId());
                return roleResponse;
            }).collect(Collectors.toList());

            creatingUserResponseDto.setRole(collects);

            response.add(creatingUserResponseDto);
        }

        return response;
    }


    @Override
    public void deleteUser(Integer userID) {
        User user = this.userRepo.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userID));
        this.userRepo.delete(user);
    }

    private User convertingDtoTOEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }

    private UserDto convertingEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setAbout(user.getAbout());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

}
