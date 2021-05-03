package com.epam.esm.exception;

public class EntityNotFoundException extends RuntimeException {
    private final int code;

    public EntityNotFoundException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
