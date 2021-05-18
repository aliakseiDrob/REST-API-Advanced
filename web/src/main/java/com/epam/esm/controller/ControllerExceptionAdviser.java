package com.epam.esm.controller;

import com.epam.esm.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionAdviser extends ResponseEntityExceptionHandler {

    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public ControllerExceptionAdviser(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(TagValidationException.class)
    public ResponseEntity<Object> handleTagValidationException(TagValidationException ex, Locale locale) {
        return createResponseEntity(ex.getCode(),locale,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CertificateValidationException.class)
    public ResponseEntity<Object> handleCertificateValidationException(CertificateValidationException ex, Locale locale) {
        return createResponseEntity(ex.getCode(),locale,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, Locale locale) {
        return createResponseEntity(ex.getCode(),locale,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaginationPageException.class)
    public ResponseEntity<Object> handlePaginationPageException(PaginationPageException ex, Locale locale) {
        return createResponseEntity(ex.getCode(),locale,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TagEntityException.class)
    public ResponseEntity<Object> handleTagEntityException(TagEntityException ex, Locale locale) {
        return createResponseEntity(ex.getCode(),locale,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return createResponseEntity(40405,request.getLocale(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleUnregisterException(Locale locale) {
        return createResponseEntity(50001,locale,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String defineTypeMessage(int code) {
        return "exception.message." + code;
    }

    private ResponseEntity<Object> createResponseEntity(int code, Locale locale,HttpStatus httpStatus) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("errorMessage", messageSource.getMessage(defineTypeMessage(code), null, locale));
        parameters.put("errorCode", code);
        return new ResponseEntity<>(parameters, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return createResponseEntity(40010,request.getLocale(),HttpStatus.BAD_REQUEST);
    }
}