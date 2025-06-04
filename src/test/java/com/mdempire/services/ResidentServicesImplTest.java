package com.mdempire.services;

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
import com.mdempire.exceptions.GatePassException;
import com.mdempire.exceptions.ResidentDoesNotExistException;
import com.mdempire.exceptions.ResidentExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ResidentServicesImplTest {

    @Autowired
    private Residents residentRepository;
    @Autowired
    private ResidentServices residentServices;
    @Autowired
    private Visitors visitorsRepository;
    @Autowired
    private AccessTokens accessTokenRepository;

    private ResidentRegisterServicesRequest registerRequest = new ResidentRegisterServicesRequest();
    private ResidentLoginServiceRequest loginRequest = new ResidentLoginServiceRequest();


    @BeforeEach
    public void setUp() {
        residentRepository.deleteAll();
        visitorsRepository.deleteAll();
    }

    @Test
    void testRegisterOneResidents_countShouldBeOne() {
        registerResident();
        assertEquals(1, residentRepository.count());
    }

    @Test
    void testRegisterMultipleResidents_countShouldBeTwo_residentRegistered() {
        registerResident();
        assertEquals(1, residentRepository.count());

        ResidentRegisterServicesRequest registerRequest2 = new ResidentRegisterServicesRequest();
        registerRequest2.setFullName("MD Empire");
        registerRequest2.setHomeAddress("Lagos");
        registerRequest2.setPhoneNumber("0704445569");
        residentServices.register(registerRequest2);

        assertEquals(2, residentRepository.count());
    }

    @Test
    void testRegisterResident_throwResidentExistException_whenResidentRegisteredWitSamePhoneNumber() {
        Resident resident = residentRepository.findByPhoneNumber(registerResident().getPhoneNumber());
        assertNotNull(resident.getId());

        GatePassException exception = assertThrows(ResidentExistException.class, () -> {
            registerResident();
        });
        assertEquals("Phone number already exists", exception.getMessage());
    }


    @Test
    void testRegisterOneResidents_countShouldBeOne_giveResponseToResident() {
        ResidentRegisterServicesResponse response = registerResident();
        assertNotNull(response);
        assertEquals("Moses Idowu", response.getFullName());
        assertEquals("0704445566", response.getPhoneNumber());
        assertEquals(1, residentRepository.count());
    }


    @Test
    public void testResidentRegistered_residentLoginSuccessful() {
        Resident residentPhoneNumber = residentRepository.findByPhoneNumber(registerResident().getPhoneNumber());
        assertNotNull(residentPhoneNumber);
        assertNotNull(residentPhoneNumber.getId());

        ResidentLoginServiceRequest loginRequest = new ResidentLoginServiceRequest();
        loginRequest.setFullName("Moses Idowu");
        loginRequest.setPhoneNumber("0704445566");
        Resident resident = residentRepository.findByPhoneNumber(loginRequest.getPhoneNumber());
        assertNotNull(resident);
        ResidentLoginServiceResponse loginResponse = residentServices.login(loginRequest);
        assertEquals("Login successful", loginResponse.getMessage());
    }

    @Test
    void testLoginWithInvalidPhoneNumber_shouldThrowResidentDoesNotExistException() {
        loginRequest.setPhoneNumber("1234567890");
        assertThrows(ResidentDoesNotExistException.class, () -> {
            residentServices.login(loginRequest);
        });
    }


    @Test
    void testResidentInviteVisitor_generateAccessToken(){
        registerResident();
        GenerateAccessTokenRequest request = new GenerateAccessTokenRequest();
        request.setResidentPhoneNumber("0704445566");
        request.setVisitorsName("Nico Yusuf");
        request.setVisitorsPhoneNumber("070444559");

        GenerateAccessTokenResponse response = residentServices.generateAccessTokenForVisitor(request);
        assertNotNull(response);
    }

    @Test
    void testResidentInviteVisitor_generateAccessToken_throwExceptionIfResidentPhoneNumberDoesNotExist(){
        registerResident();
        GenerateAccessTokenRequest request = new GenerateAccessTokenRequest();
        request.setResidentPhoneNumber("07044455600");
        request.setVisitorsName("Nico Yusuf");
        request.setVisitorsPhoneNumber("070444559");

        assertThrows(ResidentDoesNotExistException.class, () -> {
            residentServices.generateAccessTokenForVisitor(request);
        });
    }


    @Test
    void testGenerateAccessTokenIsUsed_andCannotBeUsedAgain(){
        registerResident();
        GenerateAccessTokenRequest request = new GenerateAccessTokenRequest();
        request.setResidentPhoneNumber("0704445566");
        request.setVisitorsName("Usman Yusuf");
        request.setVisitorsPhoneNumber("070444559");
        GenerateAccessTokenResponse response = residentServices.generateAccessTokenForVisitor(request);
        assertNotNull(response);
        assertEquals(1, residentRepository.count());


        FindAccessTokenRequest tokenRequest = new FindAccessTokenRequest();
        tokenRequest.setToken(response.getOtpCode());
        FindAccessTokenResponse findAccessTokenResponse = residentServices.findAccessToken(tokenRequest);
        assertNotNull(findAccessTokenResponse);
    }


    @Test
    void testRegisterOneResident_inviteTwoVisitors_generateTwoAccessToken_forTwoVisitors(){
        registerResident();
        GenerateAccessTokenRequest request = new GenerateAccessTokenRequest();
        request.setResidentPhoneNumber("0704445566");
        request.setVisitorsName("Usman Yusuf");
        request.setVisitorsPhoneNumber("070444559");
        GenerateAccessTokenResponse response = residentServices.generateAccessTokenForVisitor(request);
        assertNotNull(response);
        assertEquals(1, residentRepository.count());

        FindAccessTokenRequest tokenRequest = new FindAccessTokenRequest();
        tokenRequest.setToken(response.getOtpCode());
        FindAccessTokenResponse findAccessTokenResponse = residentServices.findAccessToken(tokenRequest);
        assertNotNull(findAccessTokenResponse);

        GenerateAccessTokenRequest request2 = new GenerateAccessTokenRequest();
        request2.setResidentPhoneNumber("0704445566");
        request2.setVisitorsName("Eric Ali");
        request2.setVisitorsPhoneNumber("070444559");
        GenerateAccessTokenResponse response2 = residentServices.generateAccessTokenForVisitor(request2);
        assertNotNull(response2);
        assertEquals(1, residentRepository.count());

        FindAccessTokenRequest tokenRequest2 = new FindAccessTokenRequest();
        tokenRequest2.setToken(response2.getOtpCode());
        FindAccessTokenResponse findAccessTokenResponse2 = residentServices.findAccessToken(tokenRequest2);
        assertNotNull(findAccessTokenResponse2);
    }

    @Test
    void testResidentInvitesThreeVisitors_generateDifferentTokens_viewAllVisitors(){
        registerResident();
        GenerateAccessTokenRequest request = new GenerateAccessTokenRequest();
        request.setResidentPhoneNumber("0704445566");
        request.setVisitorsName("Majek Olamilekan");
        request.setVisitorsPhoneNumber("070444559");
        GenerateAccessTokenResponse firstVisitorsToken = residentServices.generateAccessTokenForVisitor(request);
        assertNotNull(firstVisitorsToken.getOtpCode());
        assertEquals(1, residentRepository.count());

        GenerateAccessTokenRequest request2 = new GenerateAccessTokenRequest();
        request2.setResidentPhoneNumber("0704445566");
        request2.setVisitorsName("Hawanatulahi Ikorodu");
        request2.setVisitorsPhoneNumber("080444559");
        GenerateAccessTokenResponse secondVisitorsToken = residentServices.generateAccessTokenForVisitor(request2);
        assertNotNull(secondVisitorsToken.getOtpCode());
        assertEquals(1, residentRepository.count());

        GenerateAccessTokenRequest request3 = new GenerateAccessTokenRequest();
        request3.setResidentPhoneNumber("0704445566");
        request3.setVisitorsName("Quayyum Ariyo");
        request3.setVisitorsPhoneNumber("09004445599");
        GenerateAccessTokenResponse thirdVisitorsToken = residentServices.generateAccessTokenForVisitor(request3);
        assertNotNull(thirdVisitorsToken.getOtpCode());
        assertEquals(1, residentRepository.count());

        List<Visitor> visitorsList = residentServices.getListOfVisitors();
        assertNotNull(visitorsList);
        assertEquals(3, visitorsList.size());
    }






    private ResidentRegisterServicesResponse registerResident() {
        registerRequest.setFullName("Moses Idowu");
        registerRequest.setHomeAddress("Lagos");
        registerRequest.setPhoneNumber("0704445566");

        return residentServices.register(registerRequest);
    }

}