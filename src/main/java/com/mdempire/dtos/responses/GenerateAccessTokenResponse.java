package com.mdempire.dtos.responses;

import lombok.Data;

@Data
public class GenerateAccessTokenResponse {

    private String otpCode;
    private String residentName;
    private String residentPhoneNumber;
    private String visitorsName;
    private String visitorsPhoneNumber;
    private String otpCreatedOn;
    private String expiryDate;
    private boolean isValid;


}
