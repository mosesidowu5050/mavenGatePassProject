package com.mdempire.services;

import com.mdempire.data.models.AccessToken;
import com.mdempire.data.repositories.AccessTokens;
import com.mdempire.data.repositories.Securities;
import com.mdempire.dtos.requests.GenerateAccessTokenRequest;
import com.mdempire.dtos.requests.SecurityLoginServiceRequest;
import com.mdempire.dtos.requests.SecurityRegisterServiceRequest;
import com.mdempire.dtos.requests.SecurityVerifyTokenRequest;
import com.mdempire.dtos.responses.GenerateAccessTokenResponse;
import com.mdempire.dtos.responses.SecurityLoginServiceResponse;
import com.mdempire.dtos.responses.SecurityRegisterServiceResponse;
import com.mdempire.dtos.responses.SecurityVerifyTokenResponse;
import com.mdempire.exceptions.SecurityDoesNotExistException;
import com.mdempire.exceptions.SecurityExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class SecurityServicesImplTest {

    @Autowired
    private SecurityServices securityServices;
    @Autowired
    private Securities securitiesRepository;
    @Autowired
    private ResidentServices residentServices;
    @Autowired
    AccessTokens accessTokenRepository;


    @BeforeEach
    public void setUp() {
        securitiesRepository.deleteAll();
    }

    @Test
    public void testSecurityRegistered_countIsOne(){
        SecurityRegisterServiceResponse securityResponse = registerSecurity();
        assertNotNull(securityResponse);
        assertEquals("Registered Successfully", securityResponse.getMessage());
        assertEquals(1, securitiesRepository.count());
    }

    @Test
    public void testSecurityRegistered_withSamePhoneNumber_throwsSecurityExistException() {
        registerSecurity();
        assertNotEquals(0, securitiesRepository.count());
        assertThrows(SecurityExistException.class, () -> registerSecurity());
    }

    @Test
    public void testSecurityRegistered_withDifferentPhoneNumber_countIsTw0() {
        registerSecurity();
        assertEquals(1, securitiesRepository.count());
        registerSecondSecurity();
        assertEquals(2, securitiesRepository.count());
    }

    @Test
    public void testSecurityCanLogin_withValidDetails(){
        SecurityRegisterServiceResponse security = registerSecurity();
        SecurityLoginServiceRequest securityLoginServiceRequest = new SecurityLoginServiceRequest();
        securityLoginServiceRequest.setFullName(security.getFullName());
        securityLoginServiceRequest.setPhoneNumber(security.getPhoneNumber());

        SecurityLoginServiceResponse response = securityServices.login(securityLoginServiceRequest);
        assertNotNull(response);
        assertEquals("Login Successful", response.getMessage());
    }

    @Test
    void testLoginWithInvalidPhoneNumber_shouldThrowResidentDoesNotExistException() {
        SecurityLoginServiceRequest securityLoginServiceRequest = new SecurityLoginServiceRequest();
        securityLoginServiceRequest.setPhoneNumber("1234567898");
        assertThrows(SecurityDoesNotExistException.class, () -> {
            securityServices.login(securityLoginServiceRequest);
        });
    }


    @Test
    void testAccessTokenMarkedAsUsed_cannotBeUsedAgain(){
        GenerateAccessTokenRequest request = new GenerateAccessTokenRequest();
        request.setResidentName("Quayyum Ariyo");
        request.setResidentPhoneNumber("0704445566");
        request.setVisitorsName("John Doe");
        request.setVisitorsPhoneNumber("08001112222");

        GenerateAccessTokenResponse response = residentServices.generateAccessTokenForVisitor(request);
        assertNotNull(response);
    }

    @Test
    void testTokenVerificationFlow() {
        GenerateAccessTokenRequest request = new GenerateAccessTokenRequest();
        request.setResidentName("Bolanle Bolanle");
        request.setResidentPhoneNumber("0704445566");
        request.setVisitorsName("John Doe");
        request.setVisitorsPhoneNumber("08001112223");
        GenerateAccessTokenResponse response = residentServices.generateAccessTokenForVisitor(request);
        assertNotNull(response);

        SecurityVerifyTokenRequest verifyRequest = new SecurityVerifyTokenRequest();
        verifyRequest.setToken(response.getOtpCode());

        SecurityVerifyTokenResponse verifyResponse = securityServices.verifyAccessToken(verifyRequest);
        assertNotNull(verifyResponse);

        AccessToken usedToken = accessTokenRepository.findByOtpCode(verifyResponse.getToken());
        assertTrue(usedToken.isUsed());
        assertFalse(usedToken.isValid());
        System.out.println(usedToken);
    }



    private SecurityRegisterServiceResponse registerSecurity(){
        SecurityRegisterServiceRequest securityRegisterServiceRequest = new SecurityRegisterServiceRequest();
        securityRegisterServiceRequest.setFullName("Security Man");
        securityRegisterServiceRequest.setPhoneNumber("1234567890");
        securityRegisterServiceRequest.setHomeAddress("123 Main St");

        return securityServices.register(securityRegisterServiceRequest);
    }

    private SecurityRegisterServiceResponse registerSecondSecurity(){
        SecurityRegisterServiceRequest securityRegisterServiceRequest = new SecurityRegisterServiceRequest();
        securityRegisterServiceRequest.setFullName("Second Security Man");
        securityRegisterServiceRequest.setPhoneNumber("1234567891");
        securityRegisterServiceRequest.setHomeAddress("124 Main St");

        return securityServices.register(securityRegisterServiceRequest);
    }

}