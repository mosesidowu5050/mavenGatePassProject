package com.mdempire.exceptions;

public class SecurityDoesNotExistException extends GatePassException {
    public SecurityDoesNotExistException(String message) {
        super(message);
    }
}
