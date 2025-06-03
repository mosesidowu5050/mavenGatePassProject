package com.mdempire.dtos.requests;

import lombok.Data;

@Data
public class SecurityLoginServiceRequest {

    private String fullName;
    private String phoneNumber;

}
