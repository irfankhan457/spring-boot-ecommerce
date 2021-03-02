package com.luv2code.springbootecommerce.dto;


import lombok.Data;

@Data
public class PurchaseResponse {

    // either use Final or @NonNull annotation to enable
    // your lombok @Data automatic constructor and getter setter
    private final String orderTrackingNumber;
}
