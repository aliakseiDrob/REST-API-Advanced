package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.CertificateValidationException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.PaginationPageException;
import com.epam.esm.validator.impl.CertificateValidatorImpl;
import com.epam.esm.validator.impl.PaginationValidatorImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateDaoImpl giftCertificateDao;
    @Mock
    private TagDaoImpl tagDao;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private CertificateValidatorImpl certificateValidator;
    @Mock
    private PaginationValidatorImpl paginationValidator;
    @InjectMocks
    GiftCertificateServiceImpl service;

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static List<GiftCertificate> certificates;
    private static List<CertificateDto> certificatesDto;
    private static final CertificateDto CERTIFICATE_FOR_UPDATE = new CertificateDto(1L, "updated", "updated",
            new BigDecimal("128.01"), 11,
            LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
            LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER),Collections.emptySet());

    @BeforeAll
    public static void init() {
        certificates = Arrays.asList(new GiftCertificate(1L, "first", "for men",
                        new BigDecimal("128.01"), 11, 1,
                        LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER)),
                new GiftCertificate(2L, "second", "for men",
                        new BigDecimal("128.01"), 11, 1,
                        LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER)));
        certificatesDto = Arrays.asList(new CertificateDto(1L, "first", "for men",
                        new BigDecimal("128.01"), 11,
                        LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER), Collections.emptySet()),
                new CertificateDto(2, "second", "for men",
                        new BigDecimal("128.01"), 11,
                        LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER), Collections.emptySet()));
    }

    @Test
    public void testGetAllShouldReturnListCertificates() {
        when(giftCertificateDao.getAll()).thenReturn(certificates);
        when(modelMapper.map(certificates.get(0), CertificateDto.class)).thenReturn(certificatesDto.get(0));
        when(modelMapper.map(certificates.get(1), CertificateDto.class)).thenReturn(certificatesDto.get(1));
        assertEquals(service.getAll(), certificatesDto);
        verify(giftCertificateDao).getAll();
    }

    @Test
    void testGetAllShouldReturnPageCertificates() {
        when(giftCertificateDao.getAll(0, 2)).thenReturn(certificates);
        when(modelMapper.map(certificates.get(0), CertificateDto.class)).thenReturn(certificatesDto.get(0));
        when(modelMapper.map(certificates.get(1), CertificateDto.class)).thenReturn(certificatesDto.get(1));
        assertEquals(service.getAll(1, 2), certificatesDto);
        verify(giftCertificateDao).getAll(0, 2);
    }

    @Test
    void testGetAllShouldThrowExceptionWhenPageNotExist() {
        when(giftCertificateDao.getRowsCount()).thenReturn(1L);
        doThrow(PaginationPageException.class).when(paginationValidator).isPaginationPageExists(1, 1, 1L);
        assertThrows(PaginationPageException.class, () -> service.getAll(1, 1));
        verify(giftCertificateDao, times(1)).getRowsCount();
        verify(giftCertificateDao, times(0)).getAll(anyInt(), anyInt());
    }

    @Test
    public void testGetByIdShouldReturnCertificateWhenCertificateExists() {
        when(giftCertificateDao.getById(1L)).thenReturn(Optional.of(certificates.get(0)));
        when(modelMapper.map(any(), any())).thenReturn((certificatesDto.get(0)));
        assertEquals(certificatesDto.get(0), service.getById(1L));
        verify(giftCertificateDao, times(1)).getById(anyLong());
    }

    @Test
    public void testGetByIdShouldTrowExceptionWhenCertificateNotExists() {
        when(giftCertificateDao.getById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getById(anyLong()));
        verify(giftCertificateDao, times(1)).getById(anyLong());
    }

    @Test
    void testGetRowCountsShouldReturnCountRowsInTable() {
        when(giftCertificateDao.getRowsCount()).thenReturn(1L);
        assertEquals(service.getRowCounts(), 1L);
        verify(giftCertificateDao, times(1)).getRowsCount();
    }

    @Test
    public void testDeleteShouldThrowExceptionWhenCertificateNotExist() {
        when(giftCertificateDao.getById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(anyLong()));
        verify(giftCertificateDao, times(0)).delete(any(GiftCertificate.class));
    }

    @Test
    public void testDeleteShouldChangeIsAvailableFieldCertificate() {
        when(giftCertificateDao.getById(anyLong())).thenReturn(Optional.of(certificates.get(0)));

        service.delete(anyLong());
        verify(giftCertificateDao, times(1)).delete(certificates.get(0));
    }

    @Test
    public void testSaveShouldThrowExceptionIfCertificateNotValid() {
        doThrow(CertificateValidationException.class).when(certificateValidator).isCertificateValidForSave(certificatesDto.get(0));
        assertThrows(CertificateValidationException.class, () -> service.save(certificatesDto.get(0)));
    }

    @Test
    public void testSaveShouldReturnIdSavedCertificate() {
        doNothing().when(certificateValidator).isCertificateValidForSave(certificatesDto.get(0));
        when(modelMapper.map(any(), any())).thenReturn((certificates.get(0)));
        when(giftCertificateDao.save(certificates.get(0))).thenReturn(certificates.get(0).getId());
        assertEquals(certificates.get(0).getId(), service.save(certificatesDto.get(0)));
    }

    @Test
    public void testUpdateShouldThrowExceptionIfCertificateNotExists() {
        doNothing().when(certificateValidator).isCertificateValidForUpdate(certificatesDto.get(0));
        when(giftCertificateDao.getById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.update(certificatesDto.get(0)));
    }
    @Test
    public void testUpdateShouldThrowExceptionIfCertificateNotValid() {
        doThrow(CertificateValidationException.class).when(certificateValidator).isCertificateValidForUpdate(certificatesDto.get(0));

        assertThrows(CertificateValidationException.class, () -> service.update(certificatesDto.get(0)));
    }

    @Test
    public void testUpdateShouldReturnUpdatedCertificate() {
        doNothing().when(certificateValidator).isCertificateValidForUpdate(CERTIFICATE_FOR_UPDATE);
        when(giftCertificateDao.getById(1L)).thenReturn(Optional.of(certificates.get(0)));
        assertEquals( certificatesDto.get(1),service.update(CERTIFICATE_FOR_UPDATE));
    }
}
