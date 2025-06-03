package com.mdempire.gatePass.data.repositories;

import com.mdempire.gatePass.data.models.Visitor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Visitors extends MongoRepository<Visitor, String> {
}
