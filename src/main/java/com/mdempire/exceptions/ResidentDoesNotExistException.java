package com.mdempire.exceptions;

public class ResidentDoesNotExistException extends GatePassException{
    public ResidentDoesNotExistException(String message) {
        super(message);
    }
}
