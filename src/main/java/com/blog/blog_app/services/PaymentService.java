package com.blog.blog_app.services;

import com.blog.blog_app.entity.Payment;
import com.blog.blog_app.request_dto.CreateOrderRequest;
import com.blog.blog_app.request_dto.VerifyPaymentRequest;
import com.blog.blog_app.response_dto.CreateOrderResponse;
import com.blog.blog_app.response_dto.VerifyPaymentResponse;
import com.razorpay.RazorpayException;

public interface PaymentService {
    CreateOrderResponse createOrder(CreateOrderRequest request) throws RazorpayException;

    VerifyPaymentResponse verifyPayment(VerifyPaymentRequest request) throws RazorpayException;

    void paymentRefund(Payment payment) throws RazorpayException;
}