package com.mdempire.gatePass.dtos.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GenerateAccessTokenRequest {

    private String residentName;
    private String residentPhoneNumber;
    private String visitorsName;
    private String visitorsPhoneNumber;
    private String otpExpiryDate;

}
