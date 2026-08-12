package com.blog.blog_app.response_dto;

import com.blog.blog_app.enums.PaymentStatus;
import com.blog.blog_app.enums.PromotionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//here i did not response user or post only responed there ids

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionResponseDto {

    private Integer promotionId;

    private Integer postId;

    private PromotionStatus promotionStatus;

    private PaymentStatus paymentStatus;

    private Integer amount;

    private LocalDateTime requestedAt;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
