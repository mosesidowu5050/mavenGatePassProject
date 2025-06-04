package com.mdempire.services;


import com.mdempire.data.models.Visitor;
import com.mdempire.dtos.requests.*;
import com.mdempire.dtos.responses.*;

import java.util.List;

public interface ResidentServices {
    ResidentRegisterServicesResponse register(ResidentRegisterServicesRequest residentServicesRequest);
    ResidentLoginServiceResponse login(ResidentLoginServiceRequest loginServiceRequest);
    GenerateAccessTokenResponse generateAccessTokenForVisitor (GenerateAccessTokenRequest residentRequest);
    FindAccessTokenResponse findAccessToken(FindAccessTokenRequest tokenRequest);
    List<Visitor> getListOfVisitors();
}
