package com.blog.blog_app.serviceImpl;

import com.blog.blog_app.entity.Payment;
import com.blog.blog_app.entity.Post;
import com.blog.blog_app.entity.User;
import com.blog.blog_app.enums.PaymentStatus;
import com.blog.blog_app.enums.PostStatus;
import com.blog.blog_app.enums.VerificationResult;
import com.blog.blog_app.exceptions.BadRequestException;
import com.blog.blog_app.exceptions.ResourceNotFoundException;
import com.blog.blog_app.exceptions.UnauthorizedException;
import com.blog.blog_app.repository.PaymentRepository;
import com.blog.blog_app.repository.PostRepo;
import com.blog.blog_app.repository.UserRepo;
import com.blog.blog_app.request_dto.CreateOrderRequest;
import com.blog.blog_app.request_dto.VerifyPaymentRequest;
import com.blog.blog_app.response_dto.CreateOrderResponse;
import com.blog.blog_app.response_dto.VerifyPaymentResponse;
import com.blog.blog_app.services.ContentVerificationService;
import com.blog.blog_app.services.PaymentService;
import com.blog.blog_app.services.PostService;
import com.razorpay.*;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Autowired
    private PaymentRepository paymentReposiatory;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;
    
    @Autowired
    private PostService postService;

    @Autowired
    private ContentVerificationService contentVerificationService;

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "email", email));
    }

    private Integer calculateAmount(Integer durationInDays) {

        return switch (durationInDays) {
            case 7 -> Integer.valueOf(99);
            case 15 -> Integer.valueOf(199);
            case 30 -> Integer.valueOf(299);
            default -> throw new BadRequestException(
                    "Invalid promotion duration. Please choose 7, 15, or 30 days."
            );
        };
    }

    @Transactional
    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest request) throws RazorpayException {


        //1 first of all i want to confirm is this current user or not
        User currentUser = getCurrentUser();

        /*

        //then after i confirm is this post is of current user or not
        Post post = this.postRepo.findById(request.getPostId()).orElseThrow(()-> new ResourceNotFoundException("Post", "id", request.getPostId()));

        if(!(post.getUser().getId().equals(currentUser.getId()))) {
            throw new UnauthorizedException(post.getUser().getId());
        }
*/

        //findin post by current post Id
        Post post = this.postRepo.findById(request.getPostId()).orElseThrow(() -> new ResourceNotFoundException("Post", "id", request.getPostId()));

        //3 checking is this promotion id is of current user or not
        if (!(post.getUser().getId().equals(currentUser.getId()))) {
            throw new UnauthorizedException(post.getUser().getId());
        }


        //finding how many proffesionall links were there
        Integer professionalLinkCount = post.getProfessionalLinkCount();

        //calculating total amount
        Integer durationDaysAmount = calculateAmount(request.getDurationDays());

        //totalAmount
        Integer totalAmount = durationDaysAmount * professionalLinkCount;


        //if our post is in pending payment mode
        if (post.getPostStatus() != PostStatus.PENDING_PAYMENT) {

            throw new IllegalStateException(
                    "Post is not eligible for payment."
            );
        }


        /*//checking promotion paymentStatus
        //if payment is already set to be success then there has no need for doing payment
        if (post.getPostStatus().equals(PaymentStatus.SUCCESS_Payment)) {

            throw new IllegalStateException(
                    "Payment already completed."
            );
        }*/

        //if there has payment in pending state and we want to regenrate it
        Payment existingPayment = this.paymentReposiatory.findByPostAndStatus(post, PaymentStatus.PENDING).orElse(null);

        //if payment is not null and our status goes into pending state then payment already initiate don'nt try do do payment
        if (existingPayment != null &&
                existingPayment.getStatus() == PaymentStatus.PENDING) {

            CreateOrderResponse response = new CreateOrderResponse();

            response.setOrderId(existingPayment.getOrderId());
            response.setAmount(existingPayment.getAmount() * 100);
            response.setCurrency("INR");
            response.setKey(keyId);

            return response;
            //throw new IllegalStateException("Payment already initiated.");
        }


        RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);//here i have to pass key and secret


        //our client were made now we make our order
        //first of alll wew create Object of Json and store amout currency our there
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", (totalAmount * 100));  //here we convert rupee into peise
        jsonObject.put("currency", "INR");
        jsonObject.put("receipt", "txn_" + System.currentTimeMillis());

//creating new order now request goes into razorpay server
        Order order = razorpayClient.orders.create(jsonObject);

        //setting this order into our database
        Payment payment = new Payment();
        payment.setAmount(totalAmount);
        payment.setOrderId(order.get("id").toString());
        payment.setPaymentId(null);
        payment.setStatus(PaymentStatus.PENDING);

        //bidirectional mapping
        payment.setPost(post);
        paymentReposiatory.save(payment);


        CreateOrderResponse map = this.modelMapper.map(payment, CreateOrderResponse.class);
        map.setAmount(totalAmount * 100); //because razor pay checkout want response in peise not in rupee we save in db in form of rupees
        map.setKey(keyId);
        map.setCurrency(order.get("currency").toString());

        return map;

    }

    @Override
    @Transactional
    public VerifyPaymentResponse verifyPayment(VerifyPaymentRequest request) throws RazorpayException {

        //verifying current user
        User currentUser = getCurrentUser();

        //finding oderId
        Payment payment = paymentReposiatory.findByOrderId(request.getOrderId()).orElseThrow(() -> new ResourceNotFoundException("Payment", "Order id", request.getOrderId()));

        //verifying that payment belongs to current user
        Post post = payment.getPost();
        if (!(post.getUser().getId().equals(currentUser.getId()))) {
            throw new UnauthorizedException(post.getUser().getId());
        }


        //if payment is in not in success and not in pending if it is in failed state then also payment is not done
        //if it is not equal to pending,,,,if it is come in success or failed state then throw
        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException(
                    "Payment is not in pending state."
            );
        }

        //if in my post also status is not equal to pending then also throw
        if (post.getPostStatus() != PostStatus.PENDING_PAYMENT) {
            throw new IllegalStateException(
                    "Post is not waiting for payment."
            );
        }
        //now we making jsonObejct our here
        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", request.getOrderId());
        options.put("razorpay_payment_id", request.getPaymentId());
        options.put("razorpay_signature", request.getSignature());

        boolean isValid = Utils.verifyPaymentSignature(options, keySecret);


        if (!isValid) {
            throw new RazorpayException("Signature verification failed.");
        }

        payment.setPaymentId(request.getPaymentId());
        payment.setStatus(PaymentStatus.SUCCESS);
        post.setPostStatus(PostStatus.PENDING_VERIFICATION);

        paymentReposiatory.save(payment);

        postRepo.save(post);

        //after succesfully payment we did ai verification
        VerificationResult verificationResult = postService.verifyPostContent(post.getPostId());

        VerifyPaymentResponse response = new VerifyPaymentResponse();

        if (verificationResult == VerificationResult.SAFE) {

            response.setMessage(
                    "Payment verified successfully. Your content is safe and your post has been published."
            );

            response.setPostStatus(
                    PostStatus.PUBLISHED
            );
            response.setPaymentStatus(PaymentStatus.SUCCESS);
            response.setPostId(post.getPostId());
            response.setPaymentId(request.getPaymentId());

        } else {

            response.setMessage(
                    "Your content was unsafe. Your post has been rejected and your payment has been refunded."
            );

            response.setPostStatus(
                    PostStatus.REJECTED
            );
            response.setPaymentStatus(PaymentStatus.REFUNDED);

            response.setPostId(post.getPostId());
            response.setPaymentId(request.getPaymentId());
           //we were refunding payment
            paymentRefund(payment);

        }

        return response;

    }


    @Override
    @Transactional
    public void paymentRefund(Payment payment)
            throws RazorpayException {

        RazorpayClient razorpayClient =
                new RazorpayClient(keyId, keySecret);

        JSONObject refundRequest =
                new JSONObject();

        refundRequest.put(
                "amount",
                payment.getAmount() * 100
        );

        Refund refund =
                razorpayClient.payments.refund(
                        payment.getPaymentId(),
                        refundRequest
                );

        // Razorpay refund successful
        payment.setStatus(
                PaymentStatus.REFUNDED
        );

        paymentReposiatory.save(payment);
    }
}
