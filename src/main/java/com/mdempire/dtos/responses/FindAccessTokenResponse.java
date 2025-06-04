package com.mdempire.dtos.responses;

import com.mdempire.data.models.Resident;
import com.mdempire.data.models.Visitor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Data
public class FindAccessTokenResponse {

    private String token;
    @DBRef
    private Resident resident;
    @DBRef
    private Visitor visitor;
    private String createdOn;

}
