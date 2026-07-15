package com.blog.blog_app.controllers;

import com.blog.blog_app.request_dto.JwtRequestDto;
import com.blog.blog_app.response_dto.JwtResponse;
import com.blog.blog_app.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/user")
public class LoginController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @GetMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody JwtRequestDto jwtRequestDto) {
        //i get password and username from user i first valdidate them

        authenticating(jwtRequestDto.getName(),jwtRequestDto.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequestDto.getName());

        String token = this.jwtTokenHelper.generateToken(userDetails);

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);
        return  new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }


    private void authenticating(String userName, String password) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, password);

        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

}
