package com.epam.esm.validator.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.CertificateValidationException;
import com.epam.esm.validator.CertificateValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CertificateValidatorImpl implements CertificateValidator {
    private static final int MAX_CERTIFICATE_NAME_LENGTH = 64;
    private static final int MAX_CERTIFICATE_DESCRIPTION_LENGTH = 255;

    @Override
    public void validateCertificateForSave(CertificateDto certificate) {
        validateCertificateName(certificate.getName());
        validateCertificatePrice(certificate.getPrice());
        validateCertificateDuration(certificate.getDuration());
        validateCertificateDescription(certificate.getDescription());
    }

    //package access for testing
    void validateCertificateDescription(String description) {
        if (StringUtils.isBlank(description)) {
            throw new CertificateValidationException("Certificate description can't be empty", 40007);
        }
        if (description.length() > MAX_CERTIFICATE_DESCRIPTION_LENGTH) {
            throw new CertificateValidationException("Certificate description can't be more then 255 characters", 40008);
        }
    }

    //package access for testing
    void validateCertificateDuration(int duration) {
        if (duration <= 0 || duration > 31) {
            throw new CertificateValidationException("duration must be between 1 and 31", 40006);
        }
    }

    //package access for testing
    void validateCertificatePrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CertificateValidationException("price can't be less than zero", 40005);
        }
    }

    //package access for testing
    void validateCertificateName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new CertificateValidationException("Certificate name can't be empty", 40003);
        }
        if (name.length() > MAX_CERTIFICATE_NAME_LENGTH) {
            throw new CertificateValidationException("Certificate name can't be more then 64 characters", 40004);
        }
    }

    @Override
    public void validateCertificateForUpdate(CertificateDto certificate) {
        validateUpdatedCertificateName(certificate.getName());
        validateUpdatedCertificatePrice(certificate.getPrice());
        validateUpdatedDuration(certificate.getDuration());
        validateUpdatedDescription(certificate.getDescription());
    }

    //package access for testing
    void validateUpdatedDuration(int duration) {
        if (duration != 0 && duration <= 0 || duration > 31) {
            throw new CertificateValidationException("duration must be between 1 and 31", 40006);
        }
    }

    //package access for testing
    void validateUpdatedDescription(String description) {
        if (description != null && description.isBlank()) {
            throw new CertificateValidationException("Certificate description can't be empty", 40007);
        }
        if (description != null && description.length() > MAX_CERTIFICATE_DESCRIPTION_LENGTH) {
            throw new CertificateValidationException("Certificate description can't be more then 255 characters", 40008);
        }
    }

    //package access for testing
    void validateUpdatedCertificatePrice(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CertificateValidationException("price can't be less than zero", 40005);
        }
    }

    //package access for testing
    void validateUpdatedCertificateName(String name) {
        if (name != null && name.trim().equals("")) {
            throw new CertificateValidationException("Certificate name can't be empty", 40003);
        }
        if (name != null && name.length() > MAX_CERTIFICATE_NAME_LENGTH) {
            throw new CertificateValidationException("Certificate name can't be more then 64 characters", 40004);
        }
    }
}
