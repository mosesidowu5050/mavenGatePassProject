package com.mdempire.gatePass.data.repositories;

import com.mdempire.gatePass.data.models.AccessToken;
import com.mdempire.gatePass.data.models.Visitor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccessTokens extends MongoRepository<AccessToken, String> {

    AccessToken findByOtpCode(String otpCode);
}
