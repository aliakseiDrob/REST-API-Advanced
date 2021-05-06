package com.epam.esm.dao.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestConfig.class)
@Transactional
public class GiftCertificateDaoImplTest {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final GiftCertificate CERTIFICATE_FOR_GET_BY_ID = new GiftCertificate(1L, "first", "for men",
            new BigDecimal("128.01"), 11, 1,
            LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
            LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER));

    private static final GiftCertificate CERTIFICATE_FOR_SAVE = new GiftCertificate("fourth", "for men",
            new BigDecimal("49.20"), 6, 1,
            LocalDateTime.parse("2021-03-29 20:11:10", DATE_TIME_FORMATTER),
            LocalDateTime.parse("2021-03-29 20:11:10", DATE_TIME_FORMATTER));
    private static final GiftCertificate CERTIFICATE_FOR_UPDATE = new GiftCertificate(1,"updated", "updated",
            new BigDecimal("100"), 5, 1,
            LocalDateTime.parse("2021-03-29 20:11:10", DATE_TIME_FORMATTER),
            LocalDateTime.parse("2021-03-29 20:11:10", DATE_TIME_FORMATTER));
    private static final String EMPTY_STRING = " ";
    private static final long CERTIFICATE_ID = 1L;
    private static final int START_POSITION = 0;
    private static final int QUANTITY_ITEMS = 4;
    private static final Set<String> SET_TAGS_NAMES = Collections.singleton("first");
    private static final String NAME_OR_DESCRIPTION = "en";
    private static final String ASC_SORT = "ASC";
    private static final String DESC_SORT = "DESC";

    private static List<GiftCertificate> expectedCertificateListGetAll;
    private static List<GiftCertificate> expectedCertificateListFindByTagOrDescriptionOrName;
    private static List<GiftCertificate> expectedCertificateListFindByTagNameOrNameOrDescriptionSortByNameAscByDateDesc;


    private static void initCertificateListGetAll() {
        expectedCertificateListGetAll = Arrays.asList(
                new GiftCertificate(1L, "first", "for men", new BigDecimal("128.01"), 11, 1,
                        LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER)),
                new GiftCertificate(2L, "second", "children", new BigDecimal("250.20"), 7, 1,
                        LocalDateTime.parse("2021-03-06 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-11 20:11:10", DATE_TIME_FORMATTER)),
                new GiftCertificate(3L, "third", "everybody", new BigDecimal("48.50"), 3, 1,
                        LocalDateTime.parse("2021-03-26 19:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-28 20:11:10", DATE_TIME_FORMATTER)),
                new GiftCertificate(4L, "first", "children", new BigDecimal("48.50"), 3, 1,
                        LocalDateTime.parse("2021-03-20 19:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-28 20:11:10", DATE_TIME_FORMATTER)));
    }

    private static void initCertificateListFindByTagOrDescriptionOrName() {
        expectedCertificateListFindByTagOrDescriptionOrName = Arrays.asList(
                new GiftCertificate(1L, "first", "for men", new BigDecimal("128.01"), 11, 1,
                        LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER)),
                new GiftCertificate(2L, "second", "children", new BigDecimal("250.20"), 7, 1,
                        LocalDateTime.parse("2021-03-06 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-11 20:11:10", DATE_TIME_FORMATTER)),
                new GiftCertificate(4L, "first", "children", new BigDecimal("48.50"), 3, 1,
                        LocalDateTime.parse("2021-03-20 19:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-28 20:11:10", DATE_TIME_FORMATTER)));
    }

    private static void initCertificateListFindByTagNameOrNameOrDescriptionSortByNameAscByDateDesc() {
        expectedCertificateListFindByTagNameOrNameOrDescriptionSortByNameAscByDateDesc = Arrays.asList(
                new GiftCertificate(1L, "first", "for men", new BigDecimal("128.01"), 11, 1,
                        LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER)),
                new GiftCertificate(4L, "first", "children", new BigDecimal("48.50"), 3, 1,
                        LocalDateTime.parse("2021-03-20 19:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-28 20:11:10", DATE_TIME_FORMATTER)),
                new GiftCertificate(2L, "second", "children", new BigDecimal("250.20"), 7, 1,
                        LocalDateTime.parse("2021-03-06 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-11 20:11:10", DATE_TIME_FORMATTER)));
    }

    @Autowired
    GiftCertificateDao dao;

    @BeforeAll
    static void initAllFields() {
        initCertificateListGetAll();
        initCertificateListFindByTagOrDescriptionOrName();
        initCertificateListFindByTagNameOrNameOrDescriptionSortByNameAscByDateDesc();
    }

    @Test
    @Rollback
    void testGetAllShouldReturnListCertificates() {
        assertEquals(dao.getAll(), expectedCertificateListGetAll);
    }

    @Test
    @Rollback
    void testGetAllShouldReturnRangeCertificates() {
        //then
        assertEquals(dao.getAll(START_POSITION, QUANTITY_ITEMS), expectedCertificateListGetAll);
    }

    @Test
    @Rollback
    public void testGetByIdShouldReturnCertificate() {
        //given
        Optional<GiftCertificate> expectedCertificate = Optional.of(CERTIFICATE_FOR_GET_BY_ID);
        //then
        assertEquals(expectedCertificate, dao.getById(CERTIFICATE_ID));

    }


    @Test
    @Rollback
    public void testFindByNameOrDescriptionShouldReturnListCertificates() {
        //then
        assertEquals(dao.findByNameOrDescription("en", EMPTY_STRING, EMPTY_STRING,START_POSITION,QUANTITY_ITEMS),
                expectedCertificateListFindByTagOrDescriptionOrName);
    }


    @Test
    @Rollback
    public void testFindByTagNameOrNameOrDescriptionShouldReturnListCertificates() {
        //then
        assertEquals(dao.findByTagNameOrNameOrDescription(SET_TAGS_NAMES, NAME_OR_DESCRIPTION,
                EMPTY_STRING, EMPTY_STRING,START_POSITION,QUANTITY_ITEMS),
                expectedCertificateListFindByTagOrDescriptionOrName);
    }


    @Test
    @Rollback
    public void testFindByTagNameOrNameOrDescriptionSortByNameAscByDateDescShouldReturnListCertificates() {
        //then
        assertEquals(dao.findByTagNameOrNameOrDescription(SET_TAGS_NAMES, NAME_OR_DESCRIPTION,
                ASC_SORT, DESC_SORT,START_POSITION,QUANTITY_ITEMS),
                expectedCertificateListFindByTagNameOrNameOrDescriptionSortByNameAscByDateDesc);
    }

    @Test
    @Rollback
    public void testSaveShouldSaveCertificateInDb() {
        //when
        Long savedCertificateId = dao.save(CERTIFICATE_FOR_SAVE);
        GiftCertificate savedCertificate = dao.getById(savedCertificateId).get();
        //then
        assertEquals(savedCertificate.getName(), CERTIFICATE_FOR_SAVE.getName());
        assertEquals(savedCertificate.getDescription(), CERTIFICATE_FOR_SAVE.getDescription());
        assertEquals(savedCertificate.getPrice(), CERTIFICATE_FOR_SAVE.getPrice());
        assertEquals(savedCertificate.getDuration(), CERTIFICATE_FOR_SAVE.getDuration());
    }

    @Test
    @Rollback
    public void restUpdateShouldUpdateEntity(){
        dao.update(CERTIFICATE_FOR_UPDATE);
        GiftCertificate updatedCertificate = dao.getById(CERTIFICATE_FOR_UPDATE.getId()).get();
        assertEquals(updatedCertificate.getName(), CERTIFICATE_FOR_UPDATE.getName());
        assertEquals(updatedCertificate.getDescription(), CERTIFICATE_FOR_UPDATE.getDescription());
        assertEquals(updatedCertificate.getPrice(), CERTIFICATE_FOR_UPDATE.getPrice());
        assertEquals(updatedCertificate.getDuration(), CERTIFICATE_FOR_UPDATE.getDuration());
    }
}

