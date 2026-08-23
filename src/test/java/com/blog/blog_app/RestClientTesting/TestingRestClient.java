package com.blog.blog_app.RestClientTesting;

import com.blog.blog_app.response_dto.RestTemplateTestResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;

@SpringBootTest
public class TestingRestClient {


    @Autowired
    private RestClient restClient;

    @Test
    public void gettingPost() {
        RestTemplateTestResponse body = restClient
                .get()
                .uri("https://jsonplaceholder.typicode.com/posts/2")
                .retrieve()
                .body(RestTemplateTestResponse.class);

        System.out.println(body);

    }
}
