package com.blog.blog_app.WebClientTesting;

import com.blog.blog_app.response_dto.RestTemplateTestResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootTest
public class WebClient_Testing {


    @Autowired
    private WebClient webClient;

    @Test
    void testRestTemplateGet() throws InterruptedException {
        System.out.println("Web client Started");

        //blocking state
       RestTemplateTestResponse restTemplateTestResponseMono = webClient.get().uri("https://jsonplaceholder.typicode.com/posts/2").retrieve()
                .bodyToMono(RestTemplateTestResponse.class).block();
        System.out.println(restTemplateTestResponseMono);


        //mono state
        Mono<RestTemplateTestResponse> restTemplateTestResponseMono1 = webClient.get().uri("https://jsonplaceholder.typicode.com/posts/2").retrieve()
                .bodyToMono(RestTemplateTestResponse.class);

        restTemplateTestResponseMono1.subscribe((iteam)->
        {
            System.out.println(iteam);
        });


        Thread.sleep(5000);

        System.out.println("Post Fetched Successfully");

        System.out.println("Web client Ended");
    }

    @Test
    void testRestTemplatePost() {

       /* System.out.println("testRestTemplate");

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
        System.out.println(response.getStatusCode());*/

        // Request body
        RestTemplateTestResponse restTemplateTestResponse =
                new RestTemplateTestResponse(1, 10000, "sd", "asda");

        RestTemplateTestResponse block = webClient.post().uri("https://jsonplaceholder.typicode.com/posts")
                .bodyValue(restTemplateTestResponse)
                .retrieve()
                .bodyToMono(RestTemplateTestResponse.class)
                .block();

        System.out.println(block);



    }
}
