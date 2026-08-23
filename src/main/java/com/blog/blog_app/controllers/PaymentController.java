package com.blog.blog_app.controllers;

import com.blog.blog_app.request_dto.CreateOrderRequest;
import com.blog.blog_app.request_dto.VerifyPaymentRequest;
import com.blog.blog_app.response_dto.CreateOrderResponse;
import com.blog.blog_app.response_dto.VerifyPaymentResponse;
import com.blog.blog_app.services.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request) throws RazorpayException {

        CreateOrderResponse order = paymentService.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<VerifyPaymentResponse> verifyPayment(@RequestBody VerifyPaymentRequest request) throws RazorpayException {
        VerifyPaymentResponse verifyPaymentResponse = paymentService.verifyPayment(request);

      return new ResponseEntity<>(verifyPaymentResponse, HttpStatus.OK);
    }

}
