package com.blog.blog_app.RestTemplateTesting;

import com.blog.blog_app.response_dto.RestTemplateTestResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
@SpringBootTest
public class RestTemplate_Test {

    //autowiring RestTemplate
    @Autowired
    private RestTemplate restTemplate;

    @Test
    void testRestTemplateGet()
    {
        System.out.println("testRestTemplate");

        RestTemplateTestResponse forObject = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/2", RestTemplateTestResponse.class);

        System.out.println(forObject);
    }

    @Test
    void testRestTemplatePost() {

        System.out.println("testRestTemplate");

        // Request body
        RestTemplateTestResponse restTemplateTestResponse =
                new RestTemplateTestResponse(1, 10000, "sd", "asda");

        // Request headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // Body + Headers
        HttpEntity<RestTemplateTestResponse> httpEntity =
                new HttpEntity<>(restTemplateTestResponse, httpHeaders);

        // POST request
        ResponseEntity<RestTemplateTestResponse> response =
                restTemplate.postForEntity(
                        "https://jsonplaceholder.typicode.com/posts",
                        httpEntity,
                        RestTemplateTestResponse.class
                );

        // Response body
        System.out.println(response.getBody());

        // Response status
        System.out.println(response.getStatusCode());
    }
}
