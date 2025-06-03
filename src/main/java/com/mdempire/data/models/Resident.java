package com.mdempire.gatePass.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Resident")
public class Resident {

    @Id
    private String id;
    private String fullName;
    private String homeAddress;
    private String phoneNumber;


}
