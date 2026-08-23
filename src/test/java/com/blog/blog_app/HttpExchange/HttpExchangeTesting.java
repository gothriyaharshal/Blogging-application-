package com.blog.blog_app.HttpExchange;

import com.blog.blog_app.entity.Post;
import com.blog.blog_app.response_dto.RestTemplateTestResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange(url = "https://jsonplaceholder.typicode.com")
public interface HttpExchangeTesting {

    @GetExchange("/posts/{id}")
    RestTemplateTestResponse gettingPost(@PathVariable Integer id);


    @PostExchange("/posts")
    RestTemplateTestResponse postingPost(@RequestBody Post post);


    @GetExchange("/posts")
    List<RestTemplateTestResponse> gettingAllPosts();
}
