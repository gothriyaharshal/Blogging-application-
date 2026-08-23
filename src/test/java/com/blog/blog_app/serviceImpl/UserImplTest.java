package com.blog.blog_app.serviceImpl;

import com.blog.blog_app.entity.Role;
import com.blog.blog_app.entity.User;
import com.blog.blog_app.exceptions.DuplicateEntryException;
import com.blog.blog_app.exceptions.ResourceNotFoundException;
import com.blog.blog_app.payloads.AppConstants;
import com.blog.blog_app.repository.RoleRepo;
import com.blog.blog_app.repository.UserRepo;
import com.blog.blog_app.request_dto.UserCreatingDto;
import com.blog.blog_app.response_dto.CreatingUserResponseDto;
import com.blog.blog_app.response_dto.RoleResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserImplTest {


    @Mock
    private UserRepo userRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepo roleRepo;

    @InjectMocks
    private UserImpl userImpl;

    @Test
    void createUser_ShouldCreatedSuccessfully() {


        //Flow we were following for testing this method is

        // Suppose Value comes and we get them into UserCreatingDto  and then modelMapper thse valeuue then
        // converting it into userEntity password and role were saved manially
        //userRepo .save (user) that all


        //fake request data i have to arrange our here
        UserCreatingDto userCreatingDto = new UserCreatingDto();
        userCreatingDto.setEmail("RamLal123@gmail.com");
        userCreatingDto.setPassword("12345");
        userCreatingDto.setName("RamLal123");
        userCreatingDto.setAbout("Full stack developer");

        //if this line exits in my code then
        when(userRepo.existsByEmail(userCreatingDto.getEmail())).thenReturn(false);


        //fake user entity beacuse we map dto to user
        User user = new User();
        when(modelMapper.map(userCreatingDto, User.class)).thenReturn(user);


        //if mockito see this line
        when(passwordEncoder.encode(userCreatingDto.getPassword())).thenReturn("encodedPassword");


        Role role = new Role();
        role.setRoleId(AppConstants.NORMAL_ROLE_ID);
        role.setRoleName("ROLE_USER");
        when(roleRepo.findById(AppConstants.NORMAL_ROLE_ID)).thenReturn(Optional.of(role));


        //user saving
        when(userRepo.save(user)).thenReturn(user);


        CreatingUserResponseDto creatingUserResponseDto = new CreatingUserResponseDto();
        creatingUserResponseDto.setEmail(userCreatingDto.getEmail());
        creatingUserResponseDto.setName(userCreatingDto.getName());
        creatingUserResponseDto.setAbout(userCreatingDto.getAbout());

        when(modelMapper.map(user, CreatingUserResponseDto.class)).thenReturn(creatingUserResponseDto);

        CreatingUserResponseDto result = userImpl.createUser(userCreatingDto);

        assertNotNull(result);
        assertEquals("RamLal123", result.getName());
        assertEquals("RamLal123@gmail.com", result.getEmail());
        assertNotNull(result.getRole());

        System.out.println(result.getRole());
        System.out.println(result.getId());
        System.out.println(result.getName());
        System.out.println(result.getAbout());
        System.out.println(result.getEmail());
        System.out.println("Successfully created user");
    }


    @Test
    void createUser_WithDuplicateEmail() {
        UserCreatingDto userCreatingDto = new UserCreatingDto();
        userCreatingDto.setEmail("HarshalGothriya@gmail.com");
        userCreatingDto.setPassword("12345");
        userCreatingDto.setName("RamLal123");
        userCreatingDto.setAbout("Full stack developer");

        when(userRepo.existsByEmail(userCreatingDto.getEmail())).thenReturn(true);

        assertThrows(DuplicateEntryException.class
        , ()-> userImpl.createUser(userCreatingDto)
        );

      verify(userRepo, never()).save(any(User.class));

        System.out.println("Duplicate Email");

    }

    @Test
    void createUserRoleNotFound() {

        UserCreatingDto userCreatingDto = new UserCreatingDto();
        userCreatingDto.setEmail("HarshalGothriya@gmail.com");
        userCreatingDto.setPassword("12345");
        userCreatingDto.setName("RamLal123");
        userCreatingDto.setAbout("Full stack developer");

        //first of all when i see this line of email then i throw exception
        when(userRepo.existsByEmail(userCreatingDto.getEmail())).thenReturn(false);

        //after i map dto into user
        User user = new User();
        when(modelMapper.map(userCreatingDto, User.class)).thenReturn(user);

        //then after if i see encoded line then i replace with this
        when(passwordEncoder.encode(userCreatingDto.getPassword())).thenReturn("encodedPassword");

        //then i check Role of it
        Role role = new Role();
        role.setRoleId(null);
        role.setRoleName("ROLE_USER");

        when(roleRepo.findById(AppConstants.NORMAL_ROLE_ID)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class
                , ()-> userImpl.createUser(userCreatingDto)
        );



        verify(userRepo, never()).save(any(User.class));

        System.out.println("User Role Not Found");
    }

    @Test
    void getUserByid()
    {
        Integer userId = 2;

        //fake setting user data our here
        User user = new User();
        user.setId(2);
        user.setName("Harshal");

        user.setEmail("harshal@gmail.com");
        user.setPassword("12345");
        user.setAbout("Full stack developer");

        //setting up role
        Role role = new Role();
        role.setRoleId(AppConstants.NORMAL_ROLE_ID);
        role.setRoleName("ROLE_USER");

        HashSet<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);



       when(userRepo.findById(userId)).thenReturn(Optional.of(user));

      CreatingUserResponseDto creatingUserResponseDto = new CreatingUserResponseDto();
      creatingUserResponseDto.setEmail(user.getEmail());
      creatingUserResponseDto.setName(user.getName());
      creatingUserResponseDto.setAbout(user.getAbout());

     RoleResponse roleResponse = new RoleResponse();
     roleResponse.setRoleId(AppConstants.NORMAL_ROLE_ID);
     roleResponse.setRoleName("ROLE_USER");
        List<RoleResponse> roleResponse1 = List.of(roleResponse);
        creatingUserResponseDto.setRole(roleResponse1);


        when(modelMapper.map(user, CreatingUserResponseDto.class)).thenReturn(creatingUserResponseDto);


        CreatingUserResponseDto userById = userImpl.getUserById(userId);


        assertNotNull(userById);
        assertEquals(userId, user.getId());
        assertEquals("Harshal", user.getName());
        assertEquals("Full stack developer", user.getAbout());
        assertEquals("harshal@gmail.com", user.getEmail());


    }

    @Test
    void getUserByUserId()
    {
        Integer userId = 2;

        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class , ()->
                userImpl.getUserById(userId));
    }
    }


