package com.epam.esm.exception;

public class PaginationPageException extends RuntimeException {
    private final int code;

    public PaginationPageException(String message, int code) {
        super(message);
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
