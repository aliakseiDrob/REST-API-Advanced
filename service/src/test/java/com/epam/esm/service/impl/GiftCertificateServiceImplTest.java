package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
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

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String EMPTY_STRING = "";
    private static final String PART_NAME_OR_DESCRIPTION = "partNameOrDescription";
    private static List<GiftCertificate> certificates;
    private static List<CertificateDto> certificatesDto;
    private static Set<Tag> tagsSet;
    private static Set<TagDto> tagsDtoSet;

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
    private GiftCertificateServiceImpl service;

    @BeforeAll
    public static void init() {
        initCertificatesList();
        initCertificatesDtoList();
        initTagsSet();
        initTagsDtoSet();
    }

    private static void initTagsSet() {
        tagsSet = new HashSet<>();
        tagsSet.add(new Tag(1L, "first"));
        tagsSet.add(new Tag(2L, "second"));
    }

    private static void initTagsDtoSet() {
        tagsDtoSet = new HashSet<>();
        tagsDtoSet.add(new TagDto(1L, "first"));
        tagsDtoSet.add(new TagDto(2L, "second"));
    }

    private static void initCertificatesList() {
        certificates = Arrays.asList(new GiftCertificate(1L, "first", "for men",
                        new BigDecimal("128.01"), 11, 1,
                        LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER)),
                new GiftCertificate(2L, "second", "for men",
                        new BigDecimal("128.01"), 11, 1,
                        LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER)));
    }

    private static void initCertificatesDtoList() {
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
        //when
        when(giftCertificateDao.getAll()).thenReturn(certificates);
        when(modelMapper.map(certificates.get(0), CertificateDto.class)).thenReturn(certificatesDto.get(0));
        when(modelMapper.map(certificates.get(1), CertificateDto.class)).thenReturn(certificatesDto.get(1));
        //then
        assertEquals(certificatesDto, service.getAll());
        verify(giftCertificateDao).getAll();
        verify(modelMapper, times(certificates.size())).map(any(), any());
    }

    @Test
    void testGetAllShouldReturnPageCertificates() {
        //when
        when(giftCertificateDao.getRowsCount()).thenReturn(2L);
        doNothing().when(paginationValidator).validatePaginationPage(anyInt(), anyInt(), anyLong());
        when(giftCertificateDao.getAll(0, 2)).thenReturn(certificates);
        when(modelMapper.map(certificates.get(0), CertificateDto.class)).thenReturn(certificatesDto.get(0));
        when(modelMapper.map(certificates.get(1), CertificateDto.class)).thenReturn(certificatesDto.get(1));
        //then
        assertEquals(certificatesDto, service.getAll(1, 2));
        verify(giftCertificateDao, times(1)).getRowsCount();
        verify(giftCertificateDao).getAll(0, 2);
        verify(modelMapper, times(certificates.size())).map(any(), any());
    }

    @Test
    void testGetAllShouldThrowExceptionWhenPageNotExist() {
        //when
        when(giftCertificateDao.getRowsCount()).thenReturn(1L);
        doThrow(PaginationPageException.class).when(paginationValidator).validatePaginationPage(anyInt(), anyInt(), anyLong());
        //then
        assertThrows(PaginationPageException.class, () -> service.getAll(1, 1));
        verify(giftCertificateDao, times(1)).getRowsCount();
        verify(giftCertificateDao, times(0)).getAll(anyInt(), anyInt());
        verify(modelMapper, times(0)).map(any(), any());
    }

    @Test
    public void testGetByIdShouldReturnCertificateWhenCertificateExists() {
        //when
        when(giftCertificateDao.getById(1L)).thenReturn(Optional.of(certificates.get(0)));
        when(modelMapper.map(any(), any())).thenReturn((certificatesDto.get(0)));
        //then
        assertEquals(certificatesDto.get(0), service.getById(1L));
        verify(giftCertificateDao, times(1)).getById(anyLong());
        verify(modelMapper, times(1)).map(any(), any());
    }

    @Test
    public void testGetByIdShouldTrowExceptionWhenCertificateNotExists() {
        //when
        when(giftCertificateDao.getById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(EntityNotFoundException.class, () -> service.getById(anyLong()));
        verify(giftCertificateDao, times(1)).getById(anyLong());
        verify(modelMapper, times(0)).map(any(), any());
    }

    @Test
    void testGetRowCountsShouldReturnCountRowsInTable() {
        //when
        when(giftCertificateDao.getRowsCount()).thenReturn(1L);
        //then
        assertEquals(1L, service.getRowCounts());
        verify(giftCertificateDao, times(1)).getRowsCount();
    }

    @Test
    public void testDeleteShouldThrowExceptionWhenCertificateNotExist() {
        //when
        when(giftCertificateDao.getById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(EntityNotFoundException.class, () -> service.delete(anyLong()));
        verify(giftCertificateDao, times(0)).update(any(GiftCertificate.class));

    }

    @Test
    public void testDeleteShouldChangeIsAvailableFieldCertificate() {
        //given
        GiftCertificate certificateBeforeDeleting = new GiftCertificate(1L, "first", "for men",
                new BigDecimal("128.01"), 11, 1,
                LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER), LocalDateTime.now());
        GiftCertificate certificateAfterDeleting = new GiftCertificate(1L, "first", "for men",
                new BigDecimal("128.01"), 11, 0,
                LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER), LocalDateTime.now());
        //when
        when(giftCertificateDao.getById(anyLong())).thenReturn(Optional.of(certificateBeforeDeleting));
        //then
        service.delete(1L);
        assertEquals(certificateAfterDeleting, certificateBeforeDeleting);
        verify(giftCertificateDao, times(1)).update(any());
    }

    @Test
    public void testSaveShouldThrowExceptionIfCertificateNotValid() {
        //when
        doThrow(CertificateValidationException.class).when(certificateValidator).validateCertificateForSave(certificatesDto.get(0));
        //then
        assertThrows(CertificateValidationException.class, () -> service.save(certificatesDto.get(0)));
        verify(certificateValidator, times(1)).validateCertificateForSave(any());
        verify(modelMapper, times(0)).map(any(), any());
        verify(tagDao, times(0)).saveTags(anySet());
        verify(giftCertificateDao, times(0)).save(any());
    }

    @Test
    public void testSaveShouldReturnSavedCertificateId() {
        //given
        GiftCertificate certificateBeforeSave = new GiftCertificate(1L, "first", "for men",
                new BigDecimal("128.01"), 11, 1, LocalDateTime.now(), LocalDateTime.now());
        GiftCertificate certificateWithTags = new GiftCertificate(1L, "first", "for men",
                new BigDecimal("128.01"), 11, 1, LocalDateTime.now(), LocalDateTime.now(), tagsSet);
        CertificateDto certificateDto = new CertificateDto(1L, "first", "for men",
                new BigDecimal("128.01"), 11, LocalDateTime.now(), LocalDateTime.now(), tagsDtoSet);

        //when
        doNothing().when(certificateValidator).validateCertificateForSave(any(CertificateDto.class));
        when(modelMapper.map(any(), any())).thenReturn((certificateWithTags));
        when(tagDao.saveTags(tagsSet)).thenReturn(tagsSet);
        when(giftCertificateDao.save(any(GiftCertificate.class))).thenReturn(certificateWithTags.getId());
        //then
        assertEquals(1L, service.save(certificateDto));
        assertEquals(certificateBeforeSave, certificateWithTags);
    }

    @Test
    public void testUpdateShouldThrowExceptionIfCertificateNotExists() {
        doNothing().when(certificateValidator).validateCertificateForUpdate(certificatesDto.get(0));
        when(giftCertificateDao.getById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.update(certificatesDto.get(0)));
    }

    @Test
    public void testUpdateShouldThrowExceptionIfCertificateNotValid() {
        doThrow(CertificateValidationException.class).when(certificateValidator).validateCertificateForUpdate(certificatesDto.get(0));

        assertThrows(CertificateValidationException.class, () -> service.update(certificatesDto.get(0)));
    }

    @Test
    public void testUpdateShouldReturnUpdatedCertificate() {
        //given
        CertificateDto certificateDtoForUpdate = new CertificateDto(1L, "updated", "updated",
                new BigDecimal("128.01"), 11,
                LocalDateTime.now(), LocalDateTime.now(), tagsDtoSet);
        GiftCertificate certificateFromBd = new GiftCertificate(1L, "first", "for men",
                new BigDecimal("128.01"), 11, 1,
                LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER));
        GiftCertificate updatedCertificate = new GiftCertificate(1L, "updated", "updated",
                new BigDecimal("128.01"), 11, 1,
                LocalDateTime.now(), LocalDateTime.now(), tagsSet);
        //when
        doNothing().when(certificateValidator).validateCertificateForUpdate(certificateDtoForUpdate);
        when(giftCertificateDao.getById(anyLong())).thenReturn(Optional.of(certificateFromBd));
        when(tagDao.saveTags(anySet())).thenReturn(tagsSet);
        when(modelMapper.map(new TagDto(1L, "first"), Tag.class)).thenReturn(new Tag(1L, "first"));
        when(modelMapper.map(new TagDto(2L, "second"), Tag.class)).thenReturn(new Tag(2L, "second"));
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(updatedCertificate);
        when(modelMapper.map(any(GiftCertificate.class), any())).thenReturn(certificateDtoForUpdate);
        //then
        assertEquals(certificateDtoForUpdate, service.update(certificateDtoForUpdate));
    }

    @Test
    void TestSetUpdatableFieldsShouldUpdateFieldsCertificate() {
        CertificateDto certificateDtoForUpdate = new CertificateDto(1L, "updated", "updated",
                new BigDecimal("128.01"), 11,
                LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER), LocalDateTime.now(), tagsDtoSet);
        GiftCertificate updatedCertificate = new GiftCertificate(1L, "first", "for men",
                new BigDecimal("128.01"), 11, 1,
                LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                LocalDateTime.now());
        GiftCertificate certificateAfterUpdate = new GiftCertificate(1L, "updated", "updated",
                new BigDecimal("128.01"), 11, 1,
                LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                LocalDateTime.now());
        service.setUpdatableFields(updatedCertificate, certificateDtoForUpdate);
        assertEquals(certificateAfterUpdate, updatedCertificate);
    }


    @Test
    public void testFindShouldReturnEmptyListWhenParametersNotPassed() {
        assertEquals(service.findByParameters(new HashSet<>(), EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,1,4), new ArrayList<>());
        verifyNoInteractions(giftCertificateDao);
        verifyNoInteractions(tagDao);
    }

    @Test
    public void testFindShouldReturnListCertificateDtoWhenTagNamesPassed() {
        when(giftCertificateDao.findByTagNames(Collections.singleton("name"), "nameSort", "dateSort",0,4)).thenReturn(certificates);
        when(modelMapper.map(certificates.get(0), CertificateDto.class)).thenReturn(certificatesDto.get(0));
        when(modelMapper.map(certificates.get(1), CertificateDto.class)).thenReturn(certificatesDto.get(1));
        assertEquals(service.findByParameters(Collections.singleton("name"), EMPTY_STRING, "nameSort", "dateSort",1,4), certificatesDto);
        verifyNoMoreInteractions(giftCertificateDao);
        verifyNoMoreInteractions(tagDao);
    }

    @Test
    public void testFindShouldReturnListCertificateDtoWhenPartNameOrDescriptionPassed() {
        when(giftCertificateDao.findByNameOrDescription(PART_NAME_OR_DESCRIPTION, "nameSort", "dateSort",0,4)).thenReturn(certificates);
        when(modelMapper.map(certificates.get(0), CertificateDto.class)).thenReturn(certificatesDto.get(0));
        when(modelMapper.map(certificates.get(1), CertificateDto.class)).thenReturn(certificatesDto.get(1));
        assertEquals(service.findByParameters(new HashSet<>(), PART_NAME_OR_DESCRIPTION, "nameSort", "dateSort",1,4), certificatesDto);
        verifyNoMoreInteractions(giftCertificateDao);
        verifyNoMoreInteractions(tagDao);
    }

    @Test
    public void testFindShouldReturnListCertificateDtoWhenTagNameAndPartNameOrDescriptionPassed() {
        when(giftCertificateDao.findByTagNameOrNameOrDescription(Collections.singleton("name"), PART_NAME_OR_DESCRIPTION, "nameSort", "dateSort",0,4)).thenReturn(certificates);
        when(modelMapper.map(certificates.get(0), CertificateDto.class)).thenReturn(certificatesDto.get(0));
        when(modelMapper.map(certificates.get(1), CertificateDto.class)).thenReturn(certificatesDto.get(1));
        assertEquals(service.findByParameters(Collections.singleton("name"), PART_NAME_OR_DESCRIPTION, "nameSort", "dateSort",1,4), certificatesDto);
        verifyNoMoreInteractions(giftCertificateDao);
        verifyNoMoreInteractions(tagDao);
    }
}