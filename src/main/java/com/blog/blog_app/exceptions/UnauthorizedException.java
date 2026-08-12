package com.blog.blog_app.exceptions;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ClassUtils;

@Getter
@Setter
@NoArgsConstructor
public class UnauthorizedException extends RuntimeException {

    Integer resourceId;

    public UnauthorizedException(Integer resourceId) {
        super(String.format(
                "%s you can promote only your own post %s : %d",
                "Error",
                "Post Id",
                resourceId
        ));
        this.resourceId = resourceId;
    }
}
