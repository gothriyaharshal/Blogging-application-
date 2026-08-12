package com.blog.blog_app.response_dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderResponse {
    private String orderId;
    private Integer amount;
    private String currency;
    private String key;


}
