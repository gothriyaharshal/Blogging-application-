package com.blog.blog_app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilterChainAuthentication extends OncePerRequestFilter {


    private final JwtTokenHelper jwtTokenHelper;

    private final UserDetailsService userDetailsService;


    @Autowired
    JwtFilterChainAuthentication(JwtTokenHelper jwtTokenHelper, UserDetailsService userDetailsService) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //when login api hit it goes null first beacuse we did no have any thing in our header authorization
        String requestToken = request.getHeader("Authorization");
        System.out.println("it contains whole token with bearer" + requestToken);

        //token were generated from authorization now from token i took out username
        //in my jwttoken helper there has methos

        String realToken = null;
        String realUserName = null;

        if (requestToken != null && requestToken.startsWith("Bearer")) {
            realToken = requestToken.substring(7);
            realUserName = this.jwtTokenHelper.getUsernameFromToken(realToken);

            System.out.println(realUserName);
            System.out.println(realToken);
        } else {
            System.out.println("Jwt token does not start with bearee");
        }

        //i get token from authorizatin and i get username from that token
        //now i validate that token and user name

        //when i do login then this securityContext were set at that time
        if (realToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(realUserName);

            if (jwtTokenHelper.validateToken(realToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); //here we have to pass userDetails and its role based authoritiess
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


            } else {
                System.out.println("Invalid jwtToken");
            }
        } else {
            System.out.println("user name is null or context is null");
        }
        filterChain.doFilter(request, response);
    }
}