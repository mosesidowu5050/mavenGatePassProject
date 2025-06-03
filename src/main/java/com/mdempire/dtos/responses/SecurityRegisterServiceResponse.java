package com.mdempire.gatePass.dtos.responses;

import lombok.Data;

@Data
public class SecurityRegisterServiceResponse {

    private String fullName;
    private String phoneNumber;
    private String message;

}
