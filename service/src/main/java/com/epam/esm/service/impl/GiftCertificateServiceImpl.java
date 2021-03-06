package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.ServiceUtils;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.PaginationValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final ModelMapper modelMapper;
    private final CertificateValidator certificateValidator;
    private final PaginationValidator paginationValidator;


    @Override
    public List<CertificateDto> getAll() {
        List<GiftCertificate> giftCertificates = giftCertificateDao.getAll();
        return giftCertificates.stream()
                .map(certificate -> modelMapper.map(certificate, CertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CertificateDto> getAll(int page, int items) {
        long rowsCount = giftCertificateDao.getRowsCount();
        paginationValidator.validatePaginationPage(page, items, rowsCount);
        List<GiftCertificate> giftCertificates = giftCertificateDao.getAll(ServiceUtils.calculateStartPos(page, items), items);
        return giftCertificates.stream()
                .map(certificate -> modelMapper.map(certificate, CertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CertificateDto getById(Long id) {
        GiftCertificate giftCertificate = giftCertificateDao.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gift Certificate not found", 40401));
        return modelMapper.map(giftCertificate, CertificateDto.class);
    }

    @Override
    public long getRowCounts() {
        return giftCertificateDao.getRowsCount();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        GiftCertificate certificate = giftCertificateDao.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gift Certificate not found", 40401));
        certificate.setIsAvailable(0);
        giftCertificateDao.update(certificate);
    }

    @Override
    @Transactional
    public long save(CertificateDto certificateDto) {
        if (certificateDto.getId() != 0) {
            certificateDto.setId(0);
        }
        certificateValidator.validateCertificateForSave(certificateDto);
        GiftCertificate giftCertificate = modelMapper.map(certificateDto, GiftCertificate.class);
        Set<Tag> certificateTags = tagDao.saveTags(giftCertificate.getTags());
        giftCertificate.setTags(certificateTags);
        return giftCertificateDao.save(giftCertificate);
    }


    @Override
    public List<CertificateDto> findByParameters(Set<String> tagNames,
                                                 String partOfNameOrDesc,
                                                 String nameSort,
                                                 String dateSort,
                                                 int page, int items) {
        List<GiftCertificate> giftCertificates = getCertificates(tagNames,
                partOfNameOrDesc, nameSort, dateSort, page, items);
        return giftCertificates.stream()
                .map(certificate -> modelMapper.map(certificate, CertificateDto.class))
                .collect(Collectors.toList());
    }

    private List<GiftCertificate> getCertificates(Set<String> tagNames,
                                                  String partOfNameOrDesc,
                                                  String nameSort, String dateSort, int page, int items) {
       int startPosition = ServiceUtils.calculateStartPos(page, items);
        List<GiftCertificate> certificateList = new ArrayList<>();

        if (ServiceUtils.isTagNamesPassed(tagNames) && ServiceUtils.isParameterPassed(partOfNameOrDesc)) {
            certificateList = giftCertificateDao.findByTagNameOrNameOrDescription(tagNames, partOfNameOrDesc, nameSort, dateSort,startPosition, items);
        } else {
            if (ServiceUtils.isTagNamesPassed(tagNames)) {
                certificateList = giftCertificateDao.findByTagNames(tagNames, nameSort, dateSort,startPosition, items);
            }
            if (ServiceUtils.isParameterPassed(partOfNameOrDesc)) {
                certificateList = giftCertificateDao.findByNameOrDescription(partOfNameOrDesc, nameSort, dateSort, startPosition, items);
            }
        }
        return certificateList;
    }

    @Override
    @Transactional
    public CertificateDto update(CertificateDto certificateDto) {
        certificateValidator.validateCertificateForUpdate(certificateDto);
        GiftCertificate updatedCertificate = giftCertificateDao.getById(certificateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Gift Certificate not found", 40401));
        Set<Tag> tags = certificateDto.getTags().stream()
                .map(tagDto -> modelMapper.map(tagDto, Tag.class))
                .collect(Collectors.toSet());
        Set<Tag> tagsWithId = tagDao.saveTags(tags);
        updatedCertificate.setTags(tagsWithId);
        setUpdatableFields(updatedCertificate, certificateDto);
        return modelMapper.map(giftCertificateDao.update(updatedCertificate), CertificateDto.class);
    }

    //package access for testing
    void setUpdatableFields(GiftCertificate certificate, CertificateDto certificateDto) {
        setNameIfPassed(certificate, certificateDto);
        setDescriptionIfPassed(certificate, certificateDto);
        setPriceIfPassed(certificate, certificateDto);
        setDurationIfPassed(certificate, certificateDto);

    }

    private void setDurationIfPassed(GiftCertificate certificate, CertificateDto certificateDto) {
        if (certificateDto.getDuration() != 0) {
            certificate.setDuration(certificateDto.getDuration());
        }
    }

    private void setPriceIfPassed(GiftCertificate certificate, CertificateDto certificateDto) {
        if (certificateDto.getPrice() != null) {
            certificate.setPrice(certificateDto.getPrice());
        }
    }

    private void setDescriptionIfPassed(GiftCertificate certificate, CertificateDto certificateDto) {
        if (certificateDto.getDescription() != null) {
            certificate.setDescription(certificateDto.getDescription());
        }
    }

    private void setNameIfPassed(GiftCertificate certificate, CertificateDto certificateDto) {
        if (certificateDto.getName() != null) {
            certificate.setName(certificateDto.getName());
        }
    }
}