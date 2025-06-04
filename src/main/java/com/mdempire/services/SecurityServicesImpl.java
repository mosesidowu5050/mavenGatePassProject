package com.mdempire.services;


import com.mdempire.data.models.AccessToken;
import com.mdempire.data.models.Security;
import com.mdempire.data.repositories.AccessTokens;
import com.mdempire.data.repositories.Securities;
import com.mdempire.dtos.requests.SecurityLoginServiceRequest;
import com.mdempire.dtos.requests.SecurityRegisterServiceRequest;
import com.mdempire.dtos.requests.SecurityVerifyTokenRequest;
import com.mdempire.dtos.responses.SecurityLoginServiceResponse;
import com.mdempire.dtos.responses.SecurityRegisterServiceResponse;
import com.mdempire.dtos.responses.SecurityVerifyTokenResponse;
import com.mdempire.exceptions.SecurityDoesNotExistException;
import com.mdempire.exceptions.SecurityExistException;
import com.mdempire.exceptions.TokenNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.mdempire.utils.Mapper.*;

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

        return tokenResponse(token);
    }


    private AccessToken validateTokenForUse(String otpCode) {
        AccessToken token = accessTokenRepository.findByOtpCode(otpCode);
        if (token == null) throw new TokenNotFoundException("Token not found.");
        if (!token.isValid() || token.isUsed()) throw new TokenNotFoundException("This token is invalid or has already been used.");

        token.setUsed(true);
        token.setValid(false);
        accessTokenRepository.save(token);

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
