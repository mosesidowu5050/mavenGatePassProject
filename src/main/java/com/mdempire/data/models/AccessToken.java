package com.mdempire.data.models;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document ("AccessToken")
public class AccessToken {

    @Id
    private String id;
    private String otpCode;
    private LocalDateTime otpCreatedOn = LocalDateTime.now();
    private LocalDateTime otpExpiredDate = otpCreatedOn.plusHours(5);
    private boolean isValid;
    private boolean isUsed;
    @DBRef
    private Resident resident;
    @DBRef
    private Visitor visitor;


}
