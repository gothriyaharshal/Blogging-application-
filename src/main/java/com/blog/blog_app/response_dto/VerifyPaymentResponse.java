package com.blog.blog_app.response_dto;

import com.blog.blog_app.enums.PaymentStatus;
import com.blog.blog_app.enums.PostStatus;
import com.blog.blog_app.enums.PromotionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyPaymentResponse {

    private String message;

    private Integer postId;

    private String paymentId;

    private PaymentStatus paymentStatus;

    private PostStatus postStatus;


}
