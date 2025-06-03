package com.mdempire.gatePass.data.repositories;

import com.mdempire.gatePass.data.models.Security;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Securities extends MongoRepository<Security, String> {

    boolean existsByPhoneNumber(String phoneNumber);
    Security findByPhoneNumber(String phoneNumber);
}
