package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Set;

/**
 * Interface for serving CertificateDto objects according to the business logic of CertificateDto
 */
public interface GiftCertificateService {

    /**
     * Returns all CertificatesDto
     *
     * @return list of CertificatesDto
     */
    List<CertificateDto> getAll();

    /**
     * Returns range of CertificatesDto
     *
     * @param page  number of page
     * @param items amount of CertificatesDto
     * @return list of CertificatesDto
     */
    List<CertificateDto> getAll(int page, int items);

    /**
     * Returns CertificateDto by id
     *
     * @param id CertificateDto id
     * @return CertificateDto object
     */
    CertificateDto getById(Long id);

    /**
     * finds amount rows in database
     *
     * @return amount rows in database
     */
    long getRowCounts();

    /**
     * Removes  CertificateDto
     *
     * @param id CertificateDto id
     */
    void delete(Long id);

    /**
     * Saves  CertificateDto
     *
     * @param certificate CertificateDto entity
     * @return CertificateDto id
     */
    long save(CertificateDto certificate);

    /**
     * Finds all  CertificatesDto by part name or description or TagDto name
     *
     * @param tagNames         names of TagsDto
     * @param partOfNameOrDesc part name or description
     * @param nameSort         is used to sort the result-set in ascending or descending order
     *                         by name, can be empty or ASC or DESC
     * @param dateSort         is used to sort the result-set in ascending or descending order
     *                         by name, can be empty or ASC or DESC
     * @return list of CertificatesDto
     */
    List<CertificateDto> findByParameters(@Nullable Set<String> tagNames,
                                           @Nullable String partOfNameOrDesc,
                                           @Nullable String nameSort,
                                           @Nullable String dateSort);

    /**
     * Updates  CertificateDto in database
     *
     * @param certificate CertificateDto entity
     * @return CertificateDto Entity
     */
    CertificateDto update(CertificateDto certificate);
}
