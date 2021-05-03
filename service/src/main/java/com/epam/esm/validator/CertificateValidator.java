package com.epam.esm.validator;

import com.epam.esm.dto.CertificateDto;

public interface CertificateValidator {
    void isCertificateValidForSave(CertificateDto certificate);

    void isCertificateValidForUpdate(CertificateDto certificate);
}
