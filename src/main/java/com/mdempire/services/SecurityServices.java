package com.mdempire.services;


import com.mdempire.dtos.requests.SecurityLoginServiceRequest;
import com.mdempire.dtos.requests.SecurityRegisterServiceRequest;
import com.mdempire.dtos.requests.SecurityVerifyTokenRequest;
import com.mdempire.dtos.responses.SecurityLoginServiceResponse;
import com.mdempire.dtos.responses.SecurityRegisterServiceResponse;
import com.mdempire.dtos.responses.SecurityVerifyTokenResponse;

public interface SecurityServices {

    SecurityRegisterServiceResponse register (SecurityRegisterServiceRequest securityRegisterServiceRequest);
    SecurityLoginServiceResponse login (SecurityLoginServiceRequest securityLoginServiceRequest);
    SecurityVerifyTokenResponse verifyAccessToken (SecurityVerifyTokenRequest securitVerifyTokenRequest);


}
