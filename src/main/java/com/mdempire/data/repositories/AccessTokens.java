package com.mdempire.data.repositories;


import com.mdempire.data.models.AccessToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccessTokens extends MongoRepository<AccessToken, String> {

    AccessToken findByOtpCode(String otpCode);
}
