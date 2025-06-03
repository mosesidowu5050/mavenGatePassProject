package com.mdempire.dtos.requests;

import lombok.Data;

@Data
public class ResidentLoginServiceRequest {

    private String phoneNumber;
    private String fullName;

}
