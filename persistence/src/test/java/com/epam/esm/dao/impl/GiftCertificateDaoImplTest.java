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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestConfig.class)
@Transactional
public class GiftCertificateDaoImplTest {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Optional<GiftCertificate> OPTIONAL_CERTIFICATE = Optional.of(new GiftCertificate(1L, "first", "for men",
            new BigDecimal("128.01"), 11, 1,
            LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
            LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER)));

    private static final GiftCertificate CERTIFICATE_FOR_SAVE = new GiftCertificate(5L, "fourth", "for men",
            new BigDecimal("49.20"), 6, 1,
            LocalDateTime.parse("2021-03-29 20:11:10", DATE_TIME_FORMATTER),
            LocalDateTime.parse("2021-03-29 20:11:10", DATE_TIME_FORMATTER));
    private static final String EMPTY_STRING = " ";

    private static List<GiftCertificate> expectedCertificateListGetAll;
    private static List<GiftCertificate> expectedCertificateListGetByName;
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

    private static void initCertificateListGetByName() {
        expectedCertificateListGetByName = Arrays.asList(
                new GiftCertificate(1L, "first", "for men", new BigDecimal("128.01"), 11, 1,
                        LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER)),
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
        initCertificateListGetByName();
        initCertificateListFindByTagNameOrNameOrDescriptionSortByNameAscByDateDesc();
    }

    @Test
    @Rollback
    void testGetAllShouldReturnListCertificates() {
        assertEquals(dao.getAll(), expectedCertificateListGetAll);
    }

    @Test
    @Rollback
    void testGetAllShouldReturnLimitCertificates() {
        assertEquals(dao.getAll(0, 4), expectedCertificateListGetAll);
    }

    @Test
    @Rollback
    public void testGetByIdShouldReturnCertificate() {
        assertEquals(dao.getById(1L), OPTIONAL_CERTIFICATE);

    }


    @Test
    @Rollback
    public void testFindByNameOrDescriptionShouldReturnListCertificates() {
        assertEquals(dao.findByNameOrDescription("en", EMPTY_STRING, EMPTY_STRING), expectedCertificateListFindByTagOrDescriptionOrName);
    }


    @Test
    @Rollback
    public void testFindByTagNameOrNameOrDescriptionShouldReturnListCertificates() {
        assertEquals(dao.findByTagNameOrNameOrDescription(Collections.singleton("first"), "en", EMPTY_STRING, EMPTY_STRING),
                expectedCertificateListFindByTagOrDescriptionOrName);
    }


    @Test
    @Rollback
    public void testFindByTagNameOrNameOrDescriptionSortByNameAscByDateDescShouldReturnListCertificates() {
        assertEquals(dao.findByTagNameOrNameOrDescription(Collections.singleton("first"), "en",
                "ASC", "DESC"),
                expectedCertificateListFindByTagNameOrNameOrDescriptionSortByNameAscByDateDesc);
    }

//    @Test
//    @Rollback
//    public void testSaveShouldSaveCertificateInDb() {
//        Long savedCertificateId = dao.save(CERTIFICATE_FOR_SAVE);
//        GiftCertificate savedCertificate = dao.getById(savedCertificateId).get();
//        assertEquals(savedCertificate.getName(), CERTIFICATE_FOR_SAVE.getName());
//        assertEquals(savedCertificate.getDescription(), CERTIFICATE_FOR_SAVE.getDescription());
//        assertEquals(savedCertificate.getPrice(), CERTIFICATE_FOR_SAVE.getPrice());
//        assertEquals(savedCertificate.getDuration(), CERTIFICATE_FOR_SAVE.getDuration());
//    }

}

