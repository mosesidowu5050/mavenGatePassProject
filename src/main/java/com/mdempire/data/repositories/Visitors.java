package com.mdempire.data.repositories;


import com.mdempire.data.models.Visitor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Visitors extends MongoRepository<Visitor, String> {
    Visitor findByReceivedToken(String token);
}
