package com.mdempire.controllers;

import com.mdempire.dtos.responses.ApiResponse;
import com.mdempire.exceptions.GatePassException;
import org.springframework.beans.factory.annotation.Autowired;
import com.mdempire.data.models.Visitor;
import com.mdempire.dtos.requests.FindAccessTokenRequest;
import com.mdempire.dtos.requests.GenerateAccessTokenRequest;
import com.mdempire.dtos.requests.ResidentRegisterServicesRequest;
import com.mdempire.dtos.responses.FindAccessTokenResponse;
import com.mdempire.dtos.responses.GenerateAccessTokenResponse;
import com.mdempire.dtos.responses.ResidentRegisterServicesResponse;
import com.mdempire.services.ResidentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ResidentController {

    @Autowired
    private ResidentServices residentServices;

    @PostMapping("/resident/register")
    public ResponseEntity<?> registerResidentService(@RequestBody ResidentRegisterServicesRequest residentServicesRequest) {
        try {
            ResidentRegisterServicesResponse response = residentServices.register(residentServicesRequest);
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.CREATED);
        } catch (GatePassException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/resident/generate/code")
    public ResponseEntity<?> generateAccessTokenForVisitor(@RequestBody GenerateAccessTokenRequest residentRequest) {
        try {
            GenerateAccessTokenResponse tokenResponse = residentServices.generateAccessTokenForVisitor(residentRequest);
            return new ResponseEntity<>(new ApiResponse(tokenResponse, true), HttpStatus.OK);
        }
        catch (GatePassException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/resident/find/code")
    public ResponseEntity<?> findAccessTokenForVisitor(@RequestBody FindAccessTokenRequest residentRequest) {
        try {
            FindAccessTokenResponse foundToken = residentServices.findAccessToken(residentRequest);
            return new ResponseEntity<>(new ApiResponse(foundToken, true), HttpStatus.ACCEPTED);
        }
        catch (GatePassException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    
    @PostMapping("/resident/listsOfVisitors")
    public List<Visitor> getListOfVisitors() {
        return residentServices.getListOfVisitors();
    }

}
