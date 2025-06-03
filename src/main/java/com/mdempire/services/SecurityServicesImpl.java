package com.mdempire.gatePass.services;

import com.mdempire.gatePass.data.models.AccessToken;
import com.mdempire.gatePass.data.models.Resident;
import com.mdempire.gatePass.data.models.Security;
import com.mdempire.gatePass.data.repositories.AccessTokens;
import com.mdempire.gatePass.data.repositories.Securities;
import com.mdempire.gatePass.dtos.requests.SecurityLoginServiceRequest;
import com.mdempire.gatePass.dtos.requests.SecurityRegisterServiceRequest;
import com.mdempire.gatePass.dtos.requests.SecurityVerifyTokenRequest;
import com.mdempire.gatePass.dtos.responses.SecurityLoginServiceResponse;
import com.mdempire.gatePass.dtos.responses.SecurityRegisterServiceResponse;
import com.mdempire.gatePass.dtos.responses.SecurityVerifyTokenResponse;
import com.mdempire.gatePass.exceptions.ResidentExistException;
import com.mdempire.gatePass.exceptions.SecurityDoesNotExistException;
import com.mdempire.gatePass.exceptions.SecurityExistException;
import com.mdempire.gatePass.exceptions.TokenNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.mdempire.gatePass.utils.Mapper.*;

@Service
public class SecurityServicesImpl implements SecurityServices {

    @Autowired
    Securities securitiesRepository;

    @Autowired
    AccessTokens accessTokenRepository;

    @Override
    public SecurityRegisterServiceResponse register(SecurityRegisterServiceRequest securityRegisterServiceRequest) {
        validatePhoneNumber(securityRegisterServiceRequest);
        Security security = securityRegisterMapper(securityRegisterServiceRequest);
        Security savedSecurity = securitiesRepository.save(security);

        return securityResponseMapper(savedSecurity);
    }

    @Override
    public SecurityLoginServiceResponse login(SecurityLoginServiceRequest securityLoginServiceRequest) {
        validateLoginDetails(securityLoginServiceRequest);

        return loginResponse();
    }

    @Override
    public SecurityVerifyTokenResponse verifyAccessToken(SecurityVerifyTokenRequest request) {
        AccessToken token = validateTokenForUse(request.getToken());
        token.setUsed(true);
        accessTokenRepository.save(token);

        return tokenResponse(token);
    }


    private AccessToken validateTokenForUse(String otpCode) {
        AccessToken token = accessTokenRepository.findByOtpCode(otpCode);
        if (token.isUsed() && !token.isValid()) throw new TokenNotFoundException("This token has already been used.");

        return token;
    }



    private AccessToken validateOtpCode(SecurityVerifyTokenRequest securityVerifyTokenRequest){
        AccessToken token = accessTokenRepository.findByOtpCode(securityVerifyTokenRequest.getToken());
        if(token == null) throw new TokenNotFoundException("Token not found");
        return token;
    }

    private void validatePhoneNumber(SecurityRegisterServiceRequest securityRegisterServiceRequest) {
        boolean phoneNumberExists = securitiesRepository.existsByPhoneNumber(securityRegisterServiceRequest.getPhoneNumber());
        if (phoneNumberExists) throw new SecurityExistException("Security already exist");
    }

    private void validateLoginDetails(SecurityLoginServiceRequest securityLoginServiceRequest) {
        Security phoneNumberFound = securitiesRepository.findByPhoneNumber(securityLoginServiceRequest.getPhoneNumber());
        if(phoneNumberFound == null || !phoneNumberFound.getFullName().equals(securityLoginServiceRequest.getFullName())) throw new SecurityDoesNotExistException("Phone number doesn't exist");
    }


}
