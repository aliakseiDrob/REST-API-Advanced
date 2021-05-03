package com.epam.esm.exception;

public class TagValidationException extends RuntimeException {
    private final int code;

    public TagValidationException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
