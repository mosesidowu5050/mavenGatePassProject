package com.mdempire.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Security")
public class Security {

    @Id
    private String id;
    private String fullName;
    private String phoneNumber;
    private String homeAddress;
    private String verifiedOtp;
    @DBRef
    private Resident resident;
    @DBRef
    private Visitor visitor;

}
