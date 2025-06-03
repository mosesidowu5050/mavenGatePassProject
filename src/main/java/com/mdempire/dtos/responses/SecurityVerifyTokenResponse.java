package com.mdempire.gatePass.dtos.responses;

import com.mdempire.gatePass.data.models.Resident;
import com.mdempire.gatePass.data.models.Visitor;
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
