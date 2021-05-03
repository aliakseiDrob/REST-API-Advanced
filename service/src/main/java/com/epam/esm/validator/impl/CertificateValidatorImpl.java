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
    public void isCertificateValidForSave(CertificateDto certificate) {
        isCertificateNameValid(certificate.getName());
        isPriceValid(certificate.getPrice());
        isDurationValid(certificate.getDuration());
        isDescriptionValid(certificate.getDescription());
    }

    private void isDescriptionValid(String description) {
        if (StringUtils.isBlank(description)) {
            throw new CertificateValidationException("Certificate description can't be empty", 40007);
        }
        if (description.length() > MAX_CERTIFICATE_DESCRIPTION_LENGTH) {
            throw new CertificateValidationException("Certificate description can't be more then 255 characters", 40008);
        }
    }

    private void isDurationValid(int duration) {
        if (duration <= 0 || duration > 31) {
            throw new CertificateValidationException("duration must be between 1 and 31", 40006);
        }
    }

    private void isPriceValid(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CertificateValidationException("price can't be less than zero", 40005);
        }
    }

    private void isCertificateNameValid(String name) {
        if (StringUtils.isBlank(name)) {
            throw new CertificateValidationException("Certificate name can't be empty", 40003);
        }
        if (name.length() > MAX_CERTIFICATE_NAME_LENGTH) {
            throw new CertificateValidationException("Certificate name can't be more then 64 characters", 40004);
        }
    }

    @Override
    public void isCertificateValidForUpdate(CertificateDto certificate) {
        isUpdatedCertificateNameValid(certificate.getName());
        isUpdatedPriceValid(certificate.getPrice());
        isUpdatedDurationValid(certificate.getDuration());
        isUpdatedDescriptionValid(certificate.getDescription());
    }

    private void isUpdatedDurationValid(int duration) {
        if (duration!=0 && duration <= 0 || duration > 31) {
            throw new CertificateValidationException("duration must be between 1 and 31", 40006);
        }
    }

    private void isUpdatedDescriptionValid(String description) {
        if (description != null && description.trim().equals("")) {
            throw new CertificateValidationException("Certificate description can't be empty", 40007);
        }
        if (description != null && description.length() > MAX_CERTIFICATE_DESCRIPTION_LENGTH) {
            throw new CertificateValidationException("Certificate description can't be more then 255 characters", 40008);
        }
    }

    private void isUpdatedPriceValid(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CertificateValidationException("price can't be less than zero", 40005);
        }
    }

    private void isUpdatedCertificateNameValid(String name) {
        if (name != null && name.trim().equals("")) {
            throw new CertificateValidationException("Certificate name can't be empty", 40003);
        }
        if (name != null && name.length() > MAX_CERTIFICATE_NAME_LENGTH) {
            throw new CertificateValidationException("Certificate name can't be more then 64 characters", 40004);
        }
    }
}
