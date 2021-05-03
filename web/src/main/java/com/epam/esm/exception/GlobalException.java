package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public class GlobalException extends RuntimeException{
    private int errorCode;
    private HttpStatus status;

    public int getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public GlobalException(String message, int errorCode, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException() {
    }

    public GlobalException(Exception ex) {
        super(ex);
    }

    public GlobalException(String message, Exception ex) {
        super(message, ex);
    }
}
