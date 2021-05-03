package com.epam.esm.exception;

public class CertificateValidationException extends RuntimeException {
    private final int code;

    public CertificateValidationException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
