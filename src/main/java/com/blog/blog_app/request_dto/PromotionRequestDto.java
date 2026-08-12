package com.blog.blog_app.request_dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ClassUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionRequestDto {

    //at this time when user requested for promotion it did not send
    /*
    id
    paymentStatus
    promotionstatus
    verifiedAt
    admintRemark
    requestAt
    * */

    //user only select i want to promote only this sepcif post with this id
    //and for that particular time
    //there has amount decide like for 7 days 99 , for 15 days 199 and for 30 days 299


    @NotNull(message = "Post id is required")
    private Integer postId;


    @NotNull(message = "Duration is required")
    private Integer durationInDays;

}
