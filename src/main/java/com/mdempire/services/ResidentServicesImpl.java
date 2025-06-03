package com.mdempire.gatePass.services;

import com.mdempire.gatePass.data.models.AccessToken;
import com.mdempire.gatePass.data.models.Resident;
import com.mdempire.gatePass.data.models.Visitor;
import com.mdempire.gatePass.data.repositories.AccessTokens;
import com.mdempire.gatePass.data.repositories.Residents;
import com.mdempire.gatePass.data.repositories.Visitors;
import com.mdempire.gatePass.dtos.requests.FindAccessTokenRequest;
import com.mdempire.gatePass.dtos.requests.ResidentLoginServiceRequest;
import com.mdempire.gatePass.dtos.requests.ResidentRegisterServicesRequest;
import com.mdempire.gatePass.dtos.requests.GenerateAccessTokenRequest;
import com.mdempire.gatePass.dtos.responses.FindAccessTokenResponse;
import com.mdempire.gatePass.dtos.responses.GenerateAccessTokenResponse;
import com.mdempire.gatePass.dtos.responses.ResidentLoginServiceResponse;
import com.mdempire.gatePass.dtos.responses.ResidentRegisterServicesResponse;
import com.mdempire.gatePass.exceptions.ResidentDoesNotExistException;
import com.mdempire.gatePass.exceptions.ResidentExistException;
import com.mdempire.gatePass.exceptions.TokenNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mdempire.gatePass.utils.Mapper.*;

@Service
public class ResidentServicesImpl implements ResidentServices {

    @Autowired
    private Residents residentRepository;
    @Autowired
    private Visitors visitorRepository;
    @Autowired
    private AccessTokens accessTokenRepository;



    @Override
    public ResidentRegisterServicesResponse register(ResidentRegisterServicesRequest residentServicesRequest) {
        verifyPhoneNumber(residentServicesRequest);
        Resident resident = accessTokenMapper(residentServicesRequest);
        Resident savedResident = residentRepository.save(resident);
        return response(savedResident);
    }

    @Override
    public ResidentLoginServiceResponse login(ResidentLoginServiceRequest loginServiceRequest) {
        validateResidentIDAndPhoneNumber(loginServiceRequest);
        return MapResponse();
    }


    @Override
    public GenerateAccessTokenResponse generateAccessTokenForVisitor(GenerateAccessTokenRequest residentRequest) {
        Resident residentPhoneNumber = residentRepository.findByPhoneNumber(residentRequest.getResidentPhoneNumber());
        if (residentPhoneNumber == null) throw new ResidentDoesNotExistException("Resident Phone Number Not Found");

        Visitor visitorsInformation = visitorsInformationMapper(residentRequest);
        Visitor savedVisitor = visitorRepository.save(visitorsInformation);
        AccessToken token = accessTokensInformationMapper(residentPhoneNumber, savedVisitor);
        AccessToken savedToken = accessTokenRepository.save(token);

        return generateAccessTokenResponseMapper(savedToken);
    }



    @Override
    public FindAccessTokenResponse findAccessToken(FindAccessTokenRequest tokenRequest) {
        String otpCode = tokenRequest.getToken();

        AccessToken tokenFound = accessTokenRepository.findByOtpCode(otpCode);
        if (tokenFound == null) throw new TokenNotFoundException("Token not found for code: " + otpCode);

        FindAccessTokenResponse response = new FindAccessTokenResponse();
        response.setToken(tokenFound.getOtpCode());
        response.setTokenValid(tokenFound.isValid());
        response.setTokenUsed(tokenFound.isUsed());

        return response;
    }

    @Override
    public List<Visitor> getListOfVisitors() {
        return visitorRepository.findAll();
    }





    private void verifyPhoneNumber(ResidentRegisterServicesRequest request) {
        boolean phoneNumberExists = residentRepository.existsByPhoneNumber(request.getPhoneNumber());
        if (phoneNumberExists) throw new ResidentExistException("Phone number already exists");
    }

    private void validateResidentIDAndPhoneNumber(ResidentLoginServiceRequest request) {
        Resident residentPhoneNumber = residentRepository.findByPhoneNumber(request.getPhoneNumber());
        boolean isNotValid = residentPhoneNumber == null;
        if (isNotValid) throw new ResidentDoesNotExistException("Invalid name or phone number");
    }

}
