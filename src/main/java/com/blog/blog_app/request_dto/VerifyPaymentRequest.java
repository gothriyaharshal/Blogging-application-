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
public class VerifyPaymentRequest {
    @NotNull(message = "Please provide a valid orderId")
    private String orderId;
    @NotNull(message = "Please provide a valid patmentId")
    private String paymentId;
    @NotNull(message = "Please provide a valid signature")
    private String signature;
}
