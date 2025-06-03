package com.mdempire.data.repositories;


import com.mdempire.data.models.Security;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Securities extends MongoRepository<Security, String> {

    boolean existsByPhoneNumber(String phoneNumber);
    Security findByPhoneNumber(String phoneNumber);
}
