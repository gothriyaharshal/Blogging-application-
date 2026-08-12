package com.blog.blog_app.repository;

import com.blog.blog_app.entity.Payment;
import com.blog.blog_app.entity.Post;
import com.blog.blog_app.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {

    Optional<Payment> findByOrderId(String orderId);


    Optional<Payment> findByPost(Post post);

    Optional<Payment> findByPostAndStatus(
            Post post,
            PaymentStatus status
    );
}
