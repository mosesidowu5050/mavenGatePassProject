package com.mdempire.gatePass.dtos.requests;

import lombok.Data;

@Data
public class SecurityRegisterServiceRequest {

    private String fullName;
    private String homeAddress;
    private String phoneNumber;

}
