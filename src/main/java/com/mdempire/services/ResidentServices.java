package com.mdempire.services;


import com.mdempire.data.models.Visitor;
import com.mdempire.dtos.requests.FindAccessTokenRequest;
import com.mdempire.dtos.requests.GenerateAccessTokenRequest;
import com.mdempire.dtos.requests.ResidentLoginServiceRequest;
import com.mdempire.dtos.requests.ResidentRegisterServicesRequest;
import com.mdempire.dtos.responses.FindAccessTokenResponse;
import com.mdempire.dtos.responses.GenerateAccessTokenResponse;
import com.mdempire.dtos.responses.ResidentLoginServiceResponse;
import com.mdempire.dtos.responses.ResidentRegisterServicesResponse;

import java.util.List;

public interface ResidentServices {
    ResidentRegisterServicesResponse register(ResidentRegisterServicesRequest residentServicesRequest);
    ResidentLoginServiceResponse login(ResidentLoginServiceRequest loginServiceRequest);
    GenerateAccessTokenResponse generateAccessTokenForVisitor (GenerateAccessTokenRequest residentRequest);
    FindAccessTokenResponse findAccessToken(FindAccessTokenRequest tokenRequest);
    List<Visitor> getListOfVisitors();
}
