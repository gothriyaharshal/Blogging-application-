package com.blog.blog_app.request_dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    @NotNull
    private Integer postId;

    @NotNull
    private Integer durationDays;

}
