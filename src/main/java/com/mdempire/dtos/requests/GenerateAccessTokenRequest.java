package com.mdempire.dtos.requests;

import lombok.Data;

@Data
public class GenerateAccessTokenRequest {

    private String residentName;
    private String residentPhoneNumber;
    private String visitorsName;
    private String visitorsPhoneNumber;
    private String visitorsAddress;
    private String otpExpiryDate;

}
