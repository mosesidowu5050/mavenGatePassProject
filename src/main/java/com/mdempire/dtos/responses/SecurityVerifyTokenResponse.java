package com.mdempire.dtos.responses;


import com.mdempire.data.models.Resident;
import com.mdempire.data.models.Visitor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class SecurityVerifyTokenResponse {

    private String token;
    @DBRef
    private Resident resident;
    @DBRef
    private Visitor visitor;
    private boolean isValidToken = true;

}
