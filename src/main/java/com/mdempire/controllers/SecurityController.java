package com.mdempire.controllers;


import com.mdempire.dtos.requests.SecurityLoginServiceRequest;
import com.mdempire.dtos.requests.SecurityRegisterServiceRequest;
import com.mdempire.dtos.requests.SecurityVerifyTokenRequest;
import com.mdempire.dtos.responses.ApiResponse;
import com.mdempire.exceptions.GatePassException;
import com.mdempire.services.SecurityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/security")

public class SecurityController {

    @Autowired
    private SecurityServices securityServices;

    @PostMapping("/register")
    public ResponseEntity<?> registerSecurity(@RequestBody SecurityRegisterServiceRequest request) {
        try {
            return new ResponseEntity<>(new ApiResponse(securityServices.register(request), true), CREATED);
        }
        catch (GatePassException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginSecurity(@RequestBody SecurityLoginServiceRequest request) {
        try {
            return new ResponseEntity<>(new ApiResponse(securityServices.login(request), true), OK);
        }
        catch (GatePassException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), BAD_REQUEST);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestBody SecurityVerifyTokenRequest request) {
        try {
            return new ResponseEntity<>(new ApiResponse(securityServices.verifyAccessToken(request), true), OK);
        }
        catch (GatePassException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), BAD_REQUEST);
        }
    }
}
