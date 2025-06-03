package com.mdempire.gatePass.services;

import com.mdempire.gatePass.dtos.requests.SecurityLoginServiceRequest;
import com.mdempire.gatePass.dtos.requests.SecurityRegisterServiceRequest;
import com.mdempire.gatePass.dtos.requests.SecurityVerifyTokenRequest;
import com.mdempire.gatePass.dtos.responses.SecurityLoginServiceResponse;
import com.mdempire.gatePass.dtos.responses.SecurityRegisterServiceResponse;
import com.mdempire.gatePass.dtos.responses.SecurityVerifyTokenResponse;

public interface SecurityServices {

    SecurityRegisterServiceResponse register (SecurityRegisterServiceRequest securityRegisterServiceRequest);
    SecurityLoginServiceResponse login (SecurityLoginServiceRequest securityLoginServiceRequest);
    SecurityVerifyTokenResponse verifyAccessToken (SecurityVerifyTokenRequest securitVerifyTokenRequest);


}
