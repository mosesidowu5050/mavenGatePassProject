package com.mdempire.services;


import com.mdempire.data.models.AccessToken;
import com.mdempire.data.models.Resident;
import com.mdempire.data.models.Visitor;
import com.mdempire.data.repositories.AccessTokens;
import com.mdempire.data.repositories.Residents;
import com.mdempire.data.repositories.Visitors;
import com.mdempire.dtos.requests.FindAccessTokenRequest;
import com.mdempire.dtos.requests.GenerateAccessTokenRequest;
import com.mdempire.dtos.requests.ResidentLoginServiceRequest;
import com.mdempire.dtos.requests.ResidentRegisterServicesRequest;
import com.mdempire.dtos.responses.FindAccessTokenResponse;
import com.mdempire.dtos.responses.GenerateAccessTokenResponse;
import com.mdempire.dtos.responses.ResidentLoginServiceResponse;
import com.mdempire.dtos.responses.ResidentRegisterServicesResponse;
import com.mdempire.exceptions.ResidentDoesNotExistException;
import com.mdempire.exceptions.ResidentExistException;
import com.mdempire.exceptions.TokenNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mdempire.utils.Mapper.*;


@Service
@Slf4j
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
        log.info("Resident saved: {}", savedResident.getFullName());

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
