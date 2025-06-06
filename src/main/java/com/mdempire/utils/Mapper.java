package com.mdempire.utils;


import com.mdempire.data.models.AccessToken;
import com.mdempire.data.models.Resident;
import com.mdempire.data.models.Security;
import com.mdempire.data.models.Visitor;
import com.mdempire.dtos.requests.GenerateAccessTokenRequest;
import com.mdempire.dtos.requests.ResidentRegisterServicesRequest;
import com.mdempire.dtos.requests.SecurityRegisterServiceRequest;
import com.mdempire.dtos.responses.*;

import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Mapper {


    public static Resident accessTokenMapper(ResidentRegisterServicesRequest request) {
        Resident resident = new Resident();
        resident.setFullName(request.getFullName());
        resident.setHomeAddress(request.getHomeAddress());
        resident.setPhoneNumber(request.getPhoneNumber());

        return resident;
    }

    public static ResidentRegisterServicesResponse response (Resident resident) {
        ResidentRegisterServicesResponse response = new ResidentRegisterServicesResponse();
        response.setFullName(resident.getFullName());
        response.setPhoneNumber(resident.getPhoneNumber());
        response.setHomeAddress(resident.getHomeAddress());

        return response;
    }

    public static ResidentLoginServiceResponse MapResponse () {
        ResidentLoginServiceResponse response = new ResidentLoginServiceResponse();
        response.setMessage("Login successful");
        return response;
    }


    public static GenerateAccessTokenResponse generateAccessTokenResponseMapper(AccessToken token) {
        GenerateAccessTokenResponse generateAccessTokenResponse = new GenerateAccessTokenResponse();
        generateAccessTokenResponse.setOtpCode(token.getOtpCode());
        generateAccessTokenResponse.setResidentName(token.getResident().getFullName());
        generateAccessTokenResponse.setResidentPhoneNumber(token.getResident().getPhoneNumber());
        generateAccessTokenResponse.setVisitorsName(token.getVisitor().getFullName());
        generateAccessTokenResponse.setVisitorsPhoneNumber(token.getVisitor().getPhoneNumber());
        generateAccessTokenResponse.setValid(true);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MM yyyy, hh:mm:ss a");
        generateAccessTokenResponse.setOtpCreatedOn(token.getOtpCreatedOn().format(formatter));
        generateAccessTokenResponse.setExpiryDate(token.getOtpExpiredDate().format(formatter));

        return generateAccessTokenResponse;
    }


    public static FindAccessTokenResponse mapFindAccessResponse(AccessToken tokenFound) {
        FindAccessTokenResponse response = new FindAccessTokenResponse();
        response.setToken(tokenFound.getOtpCode());
        response.setResident(tokenFound.getResident());
        response.setVisitor(tokenFound.getVisitor());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MM yyyy, hh:mm:ss a");
        response.setCreatedOn(tokenFound.getOtpCreatedOn().format(formatter));

        return response;
    }

    public static Resident residentsInformationMapper(Resident residentRequest) {
        Resident resident = new Resident();
        resident.setFullName(residentRequest.getFullName());
        resident.setPhoneNumber(residentRequest.getPhoneNumber());
        resident.setHomeAddress(residentRequest.getHomeAddress());

        return resident;
    }

    public static Visitor  visitorsInformationMapper(GenerateAccessTokenRequest residentRequest) {
        Visitor visitor = new Visitor();
        visitor.setFullName(residentRequest.getVisitorsName());
        visitor.setPhoneNumber(residentRequest.getVisitorsPhoneNumber());
        visitor.setHomeAddress(residentRequest.getVisitorsAddress());

        return visitor;
    }


    public static AccessToken accessTokensInformationMapper(Resident resident, Visitor savedVisitor) {
        AccessToken token = new AccessToken();
        token.setResident(resident);
        token.setVisitor(savedVisitor);
        token.setOtpCreatedOn(token.getOtpCreatedOn());
        token.setOtpCode(generateAccessToken());
        token.setUsed(false);
        token.setValid(true);

        return token;
    }


    private static String generateAccessToken() {
        char[] generateToken = {
                '1','2','3','4','5','6','7','8','9','0',
                'a','b','c','d','e','f','g','h','i','j',
                'k','l','m','n','o','p','q','r','s','t',
                'u','v','w','x','y','z'
        };

        String savedToken = "";

        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int randomToken = random.nextInt(generateToken.length);
            savedToken += generateToken[randomToken];
        }

        return savedToken;
    }


    public static SecurityRegisterServiceResponse securityResponseMapper (Security savedSecurity) {
        SecurityRegisterServiceResponse securityRegisterServiceResponse = new SecurityRegisterServiceResponse();
        securityRegisterServiceResponse.setFullName(savedSecurity.getFullName());
        securityRegisterServiceResponse.setPhoneNumber(savedSecurity.getPhoneNumber());
        securityRegisterServiceResponse.setMessage("Registered Successfully");

        return securityRegisterServiceResponse;
    }

    public static Security securityRegisterMapper(SecurityRegisterServiceRequest securityRegisterServiceRequest){
        Security security = new Security();
        security.setFullName(securityRegisterServiceRequest.getFullName());
        security.setPhoneNumber(securityRegisterServiceRequest.getPhoneNumber());
        security.setHomeAddress(securityRegisterServiceRequest.getHomeAddress());

        return security;
    }

    public static SecurityLoginServiceResponse loginResponse(){
        SecurityLoginServiceResponse securityLoginServiceResponse = new SecurityLoginServiceResponse();
        securityLoginServiceResponse.setMessage("Login Successful");
        return securityLoginServiceResponse;
    }

    public static SecurityVerifyTokenResponse tokenResponse(AccessToken token) {
        SecurityVerifyTokenResponse securityVerifyTokenResponse = new SecurityVerifyTokenResponse();
        securityVerifyTokenResponse.setToken(token.getOtpCode());

        securityVerifyTokenResponse.setResident(token.getResident());
        securityVerifyTokenResponse.setVisitor(token.getVisitor());
        securityVerifyTokenResponse.setValidToken(token.isValid());

        return securityVerifyTokenResponse;
    }
}
