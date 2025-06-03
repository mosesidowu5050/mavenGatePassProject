package com.mdempire.gatePass.data.repositories;

import com.mdempire.gatePass.data.models.Resident;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface Residents extends MongoRepository<Resident, String> {
    Optional<Resident> findByFullName(String fullName);
    Resident findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);

}
