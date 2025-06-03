package com.mdempire.gatePass.dtos.requests;

import lombok.Data;

@Data
public class SecurityLoginServiceRequest {

    private String fullName;
    private String phoneNumber;

}
