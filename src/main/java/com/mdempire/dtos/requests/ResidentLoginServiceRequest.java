package com.mdempire.gatePass.dtos.requests;

import lombok.Data;

@Data
public class ResidentLoginServiceRequest {

    private String phoneNumber;
    private String fullName;

}
