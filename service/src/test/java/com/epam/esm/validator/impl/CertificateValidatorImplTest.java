package com.epam.esm.validator.impl;

import com.epam.esm.exception.CertificateValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CertificateValidatorImplTest {

    private final static String EMPTY_STRING = "  ";
    private final static String LONG_STRING = "VERY_LONG_STRING_MORE_THEN_255_CHARACTERS_VERY_LONG_STRING_MORE_THEN_255_CHARACTERS" +
            "VERY_LONG_STRING_MORE_THEN_255_CHARACTERS_VERY_LONG_STRING_MORE_THEN_255_CHARACTERS_VERY_LONG_STRING_MORE_THEN_255_CHARACTERS" +
            "_VERY_LONG_STRING_MORE_THEN_255_CHARACTERS_VERY_LONG_STRING_MORE_THEN_255_CHARACTERS";
    private final static int INVALID_DURATION = 35;
    private final static BigDecimal INVALID_PRICE = new BigDecimal("-35");

    private final CertificateValidatorImpl validator = new CertificateValidatorImpl();


    @Test
    public void testValidateCertificateDescriptionShouldThrowExceptionIfDescriptionEmpty() {
        //then
        assertThrows(CertificateValidationException.class, () -> validator.validateCertificateDescription(EMPTY_STRING));
    }

    @Test
    public void testValidateCertificateDescriptionShouldThrowExceptionIfDescriptionMoreThanAllowed() {
        //then
        assertThrows(CertificateValidationException.class, () -> validator.validateCertificateDescription(LONG_STRING));
    }

    @Test
    public void testValidateCertificateDurationShouldThrowExceptionIfDurationInvalid() {
        //then
        assertThrows(CertificateValidationException.class, () -> validator.validateCertificateDuration(INVALID_DURATION));
    }

    @Test
    public void testValidateCertificatePriceShouldThrowExceptionIfPriceInvalid() {
        //then
        assertThrows(CertificateValidationException.class, () -> validator.validateCertificatePrice(INVALID_PRICE));
    }

    @Test
    public void testValidateCertificateNameShouldThrowExceptionIfNameEmptyString() {
        //then
        assertThrows(CertificateValidationException.class, () -> validator.validateCertificateName(EMPTY_STRING));
    }

    @Test
    public void testValidateCertificateNameShouldThrowExceptionIfNameMoreThanAllowed() {
        //then
        assertThrows(CertificateValidationException.class, () -> validator.validateCertificateName(LONG_STRING));
    }

    @Test
    public void testValidateUpdatedDurationShouldThrowExceptionIfDurationInvalid() {
        //then
        assertThrows(CertificateValidationException.class, () -> validator.validateCertificateDuration(INVALID_DURATION));
    }

    @Test
    public void testValidateUpdatedDescriptionShouldThrowExceptionIfDescriptionEmpty() {
        //then
        assertThrows(CertificateValidationException.class, () -> validator.validateCertificateDescription(EMPTY_STRING));
    }

    @Test
    public void testValidateUpdatedDescriptionShouldThrowExceptionIfDescriptionMoreThanAllowed() {
        //then
        assertThrows(CertificateValidationException.class, () -> validator.validateCertificateDescription(LONG_STRING));
    }

    @Test
    public void testValidateUpdatedCertificatePriceShouldThrowExceptionIfPriceInvalid() {
        //then
        assertThrows(CertificateValidationException.class, () -> validator.validateCertificatePrice(INVALID_PRICE));
    }

    @Test
    public void testValidateUpdatedCertificateNameShouldThrowExceptionIfNameEmptyString() {
        //then
        assertThrows(CertificateValidationException.class, () -> validator.validateCertificateName(EMPTY_STRING));
    }

    @Test
    public void testValidateUpdatedCertificateNameShouldThrowExceptionIfNameMoreThanAllowed() {
        //then
        assertThrows(CertificateValidationException.class, () -> validator.validateCertificateName(LONG_STRING));
    }
}
