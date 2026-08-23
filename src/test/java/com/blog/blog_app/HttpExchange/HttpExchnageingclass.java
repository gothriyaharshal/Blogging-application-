package com.blog.blog_app.HttpExchange;

import com.blog.blog_app.response_dto.RestTemplateTestResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class HttpExchnageingclass {


    //i want to get the post then normal method i make not overridden method

    @Autowired
    private HttpExchangeTesting httpExchangeTesting;

    @Test
    public void getPostes()
    {
        RestTemplateTestResponse restTemplateTestResponse = httpExchangeTesting.gettingPost(2);
        System.out.println(restTemplateTestResponse.getBody());
    }

    @Test
    public void getAllPostes()
    {
        List<RestTemplateTestResponse> restTemplateTestResponse = httpExchangeTesting.gettingAllPosts();
       for (RestTemplateTestResponse restTemplateTestResponse1 : restTemplateTestResponse)
       {
           System.out.println(restTemplateTestResponse1.getBody());
       }
    }

}
