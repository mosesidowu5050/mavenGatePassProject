package com.mdempire.gatePass.dtos.responses;

import lombok.Data;

@Data
public class FindAccessTokenResponse {

    private String token;
    private boolean isTokenUsed;
    private boolean isTokenValid;
    private boolean isTokenExpired;

}
