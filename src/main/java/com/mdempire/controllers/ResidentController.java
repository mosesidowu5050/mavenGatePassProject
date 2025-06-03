package com.mdempire.gatePass.controllers;

import com.mdempire.gatePass.data.models.Visitor;
import com.mdempire.gatePass.dtos.requests.FindAccessTokenRequest;
import com.mdempire.gatePass.dtos.requests.GenerateAccessTokenRequest;
import com.mdempire.gatePass.dtos.requests.ResidentRegisterServicesRequest;
import com.mdempire.gatePass.dtos.responses.FindAccessTokenResponse;
import com.mdempire.gatePass.dtos.responses.GenerateAccessTokenResponse;
import com.mdempire.gatePass.dtos.responses.ResidentRegisterServicesResponse;
import com.mdempire.gatePass.services.ResidentServices;


import java.util.List;


@RestController
@RequestMapping("/api")
public class ResidentController {

    @Autowired
    private ResidentServices residentServices;

    @PostMapping("/resident/register")
    public ResidentRegisterServicesResponse registerResidentService(@RequestBody ResidentRegisterServicesRequest residentServicesRequest) {
        return residentServices.register(residentServicesRequest);
    }

    @PostMapping("/resident/generate/code")
    public GenerateAccessTokenResponse generateAccessTokenForVisitor(@RequestBody GenerateAccessTokenRequest residentRequest) {
        return residentServices.generateAccessTokenForVisitor(residentRequest);
    }

    @PostMapping("/resident/find/code")
    public FindAccessTokenResponse findAccessTokenForVisitor(@RequestBody FindAccessTokenRequest residentRequest) {
        return residentServices.findAccessToken(residentRequest);
    }

    @PostMapping("/resident/listsOfVisitors")
    public List<Visitor> getListOfVisitors() {
        return residentServices.getListOfVisitors();
    }

}
