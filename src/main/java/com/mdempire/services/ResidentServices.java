package com.mdempire.gatePass.services;

import com.mdempire.gatePass.data.models.Visitor;
import com.mdempire.gatePass.dtos.requests.FindAccessTokenRequest;
import com.mdempire.gatePass.dtos.requests.ResidentLoginServiceRequest;
import com.mdempire.gatePass.dtos.requests.ResidentRegisterServicesRequest;
import com.mdempire.gatePass.dtos.requests.GenerateAccessTokenRequest;
import com.mdempire.gatePass.dtos.responses.FindAccessTokenResponse;
import com.mdempire.gatePass.dtos.responses.GenerateAccessTokenResponse;
import com.mdempire.gatePass.dtos.responses.ResidentLoginServiceResponse;
import com.mdempire.gatePass.dtos.responses.ResidentRegisterServicesResponse;

import java.util.List;

public interface ResidentServices {
    ResidentRegisterServicesResponse register(ResidentRegisterServicesRequest residentServicesRequest);
    ResidentLoginServiceResponse login(ResidentLoginServiceRequest loginServiceRequest);
    GenerateAccessTokenResponse generateAccessTokenForVisitor (GenerateAccessTokenRequest residentRequest);
    FindAccessTokenResponse findAccessToken(FindAccessTokenRequest tokenRequest);
    List<Visitor> getListOfVisitors();
}
