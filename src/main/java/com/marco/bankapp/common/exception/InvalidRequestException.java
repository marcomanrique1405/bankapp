package com.marco.bankapp.common.exception;

public class InvalidRequestException extends BusinessException {

    public InvalidRequestException(String code, String message) {
        super(code, message);
    }
}