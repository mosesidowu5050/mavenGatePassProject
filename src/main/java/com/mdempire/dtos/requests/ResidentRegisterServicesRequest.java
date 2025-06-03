package com.mdempire.dtos.requests;

import lombok.Data;

@Data
public class ResidentRegisterServicesRequest {

    private String fullName;
    private String homeAddress;
    private String phoneNumber;

}
