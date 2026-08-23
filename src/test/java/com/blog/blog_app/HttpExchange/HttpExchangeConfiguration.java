package com.blog.blog_app.HttpExchange;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpExchangeConfiguration {

  @Bean
    public HttpExchangeTesting httpExchangeTesting() {

      RestClient restClient = RestClient.builder()
              .baseUrl("https://jsonplaceholder.typicode.com")
              .build();


      RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);

      HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();

      return factory.createClient(HttpExchangeTesting.class);


  }

}
