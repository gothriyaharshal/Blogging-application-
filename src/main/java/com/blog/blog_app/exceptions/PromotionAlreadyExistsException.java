package com.blog.blog_app.exceptions;

import jakarta.persistence.criteria.CriteriaBuilder;

public class PromotionAlreadyExistsException extends RuntimeException {

    private final Integer promotionId;

    public PromotionAlreadyExistsException(Integer promotionId) {
        super(String.format(
                "This post already has an active promotion. Promotion Id: %d",
                promotionId
        ));
        this.promotionId = promotionId;
    }
}