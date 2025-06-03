package com.mdempire.gatePass.controllers;

import com.mdempire.gatePass.dtos.requests.SecurityLoginServiceRequest;
import com.mdempire.gatePass.dtos.requests.SecurityRegisterServiceRequest;
import com.mdempire.gatePass.dtos.requests.SecurityVerifyTokenRequest;
import com.mdempire.gatePass.dtos.responses.SecurityLoginServiceResponse;
import com.mdempire.gatePass.dtos.responses.SecurityRegisterServiceResponse;
import com.mdempire.gatePass.dtos.responses.SecurityVerifyTokenResponse;
import com.mdempire.gatePass.services.SecurityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SecurityController {

    @Autowired
    private SecurityServices securityServices;

    @PostMapping("/security/register")
    public SecurityRegisterServiceResponse registerSecurity(@RequestBody SecurityRegisterServiceRequest request) {
        return securityServices.register(request);
    }

    @PostMapping("/security/login")
    public SecurityLoginServiceResponse loginSecurity(@RequestBody SecurityLoginServiceRequest request) {
        return securityServices.login(request);
    }

    @PostMapping("/security/verify")
    public SecurityVerifyTokenResponse verifyToken(@RequestBody SecurityVerifyTokenRequest request) {
        return securityServices.verifyAccessToken(request);
    }
}

