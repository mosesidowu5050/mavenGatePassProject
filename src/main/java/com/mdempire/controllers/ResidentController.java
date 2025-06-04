package com.mdempire.controllers;

import com.mdempire.dtos.responses.*;
import com.mdempire.exceptions.GatePassException;
import org.springframework.beans.factory.annotation.Autowired;
import com.mdempire.data.models.Visitor;
import com.mdempire.dtos.requests.FindAccessTokenRequest;
import com.mdempire.dtos.requests.GenerateAccessTokenRequest;
import com.mdempire.dtos.requests.ResidentRegisterServicesRequest;
import com.mdempire.services.ResidentServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/resident")
public class ResidentController {

    @Autowired
    private ResidentServices residentServices;

    @PostMapping("/register")
    public ResponseEntity<?> registerResidentService(@RequestBody ResidentRegisterServicesRequest residentServicesRequest) {
        try {
            ResidentRegisterServicesResponse response = residentServices.register(residentServicesRequest);
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.CREATED);
        } catch (GatePassException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/generate/code")
    public ResponseEntity<?> generateAccessTokenForVisitor(@RequestBody GenerateAccessTokenRequest residentRequest) {
        try {
            GenerateAccessTokenResponse tokenResponse = residentServices.generateAccessTokenForVisitor(residentRequest);
            return new ResponseEntity<>(new ApiResponse(tokenResponse, true), HttpStatus.OK);
        }
        catch (GatePassException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/find/code")
    public ResponseEntity<?> findAccessTokenForVisitor(@RequestBody FindAccessTokenRequest residentRequest) {
        try {
            FindAccessTokenResponse foundToken = residentServices.findAccessToken(residentRequest);
            return new ResponseEntity<>(new ApiResponse(foundToken, true), HttpStatus.FOUND);
        }
        catch (GatePassException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/listsOfVisitors")
    public ResponseEntity<ApiResponse> getListOfVisitors() {
        try {
            List<Visitor> visitors = residentServices.getListOfVisitors();
            return new ResponseEntity<>(new ApiResponse(visitors, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("Failed to retrieve visitors: " + e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }


}
