package com.blog.blog_app.services;

import com.blog.blog_app.entity.Post;
import com.blog.blog_app.enums.VerificationResult;

public interface ContentVerificationService {

 VerificationResult verifyingPost(Post post) ;

}
