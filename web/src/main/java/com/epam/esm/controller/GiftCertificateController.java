package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.modelcreator.CertificateCollectionModelCreator;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * The controller provides CRUD operations on CertificateDto entity.
 */
@RestController
@RequestMapping("/giftCertificates")
@RequiredArgsConstructor
public class GiftCertificateController {

    private final GiftCertificateService service;
    private final CertificateCollectionModelCreator certificateCollectionModelCreator;

    /**
     * Finds paginate page of CertificatesDto
     *
     * @param page  required page
     * @param items number of CertificatesDto on page
     * @return the list of CertificatesDto
     */
    @GetMapping
    public CollectionModel<CertificateDto> getAll(@RequestParam(required = false, defaultValue = "1") int page,
                                                  @RequestParam(required = false, defaultValue = "10") int items) {
        List<CertificateDto> certificates = service.getAll(page, items);
        return certificateCollectionModelCreator.createModel(certificates, page, items);
    }

    /**
     * Finds CertificateDto by id
     *
     * @param id the id of CertificateDto
     * @return the CertificateDto entity
     */
    @GetMapping("/{id}")
    public CertificateDto getGiftCertificate(@PathVariable Long id) {
        return service.getById(id);
    }

    /**
     * Saves  CertificateDto
     *
     * @param certificate CertificateDto entity
     * @return CertificateDto id
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public long save(@RequestBody CertificateDto certificate) {
        return service.save(certificate);
    }

    /**
     * Finds all  CertificatesDto by part name or description or Tag name
     *
     * @param tagNames       names of Tags
     * @param partNameOrDesc part name or description
     * @param nameSort       is used to sort the list in ascending or descending order
     *                       by name, can be empty or ASC or DESC
     * @param dateSort       is used to sort the list in ascending or descending order
     *                       by name, can be empty or ASC or DESC
     * @return list of CertificatesDto
     */
    @GetMapping("/search")
    public List<CertificateDto> find(@RequestParam(required = false) Set<String> tagNames,
                                     @RequestParam(required = false, defaultValue = "") String partNameOrDesc,
                                     @RequestParam(required = false, defaultValue = "") String nameSort,
                                     @RequestParam(required = false, defaultValue = "") String dateSort,
                                     @RequestParam(required = false, defaultValue = "1") int page,
                                     @RequestParam(required = false, defaultValue = "10") int items) {
        return service.findByParameters(tagNames, partNameOrDesc, nameSort, dateSort,page,items);
    }

    /**
     * Updates  CertificateDto
     *
     * @param certificate CertificateDto entity
     * @return CertificateDto
     */
    @PatchMapping
    public CertificateDto update(@RequestBody CertificateDto certificate) {
        return service.update(certificate);
    }

    /**
     * Removes  CertificateDto
     *
     * @param id CertificateDto id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificate(@PathVariable Long id) {
        service.delete(id);
    }
}