package com.blog.blog_app.response_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class PendingPostResponse {

        private Integer professionalLinkCount;

        private String message;

        private boolean paymentRequired;

        private Integer postId;
    }
