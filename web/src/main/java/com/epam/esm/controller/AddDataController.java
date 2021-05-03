package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import com.github.javafaker.Faker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class AddDataController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AddDataController.class);
    private final TagService tagService;
    private final UserService userService;
    private final GiftCertificateService giftCertificateService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public AddDataController(TagService tagService, UserService userService,
                             GiftCertificateService giftCertificateService, OrderService orderService, ModelMapper modelMapper) {
        this.tagService = tagService;
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/addTags")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTagsToDb() {
        Faker faker = new Faker();
        for (int i = 0; i < 100; i++) {
            try {
                tagService.save(new TagDto(faker.food().ingredient() +
                        " " +
                        faker.food().ingredient()));
                LOGGER.info(i + " tag created");
            } catch (Exception exception) {
                LOGGER.warn(i + " " + exception.getMessage());
                i--;
            }
        }
    }

    @GetMapping("/addUsers")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUsersToDb() {
        Faker faker = new Faker();
        for (int i = 0; i < 100; i++) {
            try {
                userService.save(new UserDto(faker.name().fullName()));
                LOGGER.info(i + " user created");
            } catch (Exception exception) {
                LOGGER.warn(i + " " + exception.getMessage());
                i--;
            }
        }
    }

    @GetMapping("/addCertificates")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCertificatesToDb() {
        Faker faker = new Faker();
        List<TagDto> allTags = tagService.getAll();
        for (int i = 0; i < 100; i++) {
            try {
                giftCertificateService.save(createCertificate(allTags, faker));
                LOGGER.info(i + " certificate created");
            } catch (Exception exception) {
                LOGGER.warn(i + " " + exception.getMessage());
                i--;
            }
        }
    }

    private CertificateDto createCertificate(List<TagDto> allTags, Faker faker) {
        CertificateDto certificate = new CertificateDto();
        certificate.setName(faker.commerce().productName() + " " + faker.commerce().promotionCode());
        certificate.setDescription(faker.company().name() + " " + faker.commerce().color());
        certificate.setPrice(new BigDecimal(faker.commerce().price().replace(",", ".")));
        certificate.setDuration(faker.number().randomDigit());
        certificate.setTags(createCertificateTagsSet(allTags, faker.number().numberBetween(1, 5), faker));
        return certificate;
    }

    private Set<TagDto> createCertificateTagsSet(List<TagDto> tags, int maxCount, Faker faker) {
        Set<TagDto> certificateTags = new HashSet<>();
        for (int i = 0; i < maxCount; i++) {
            certificateTags.add(tags.get(faker.number().numberBetween(0, tags.size() - 1)));
        }
        return certificateTags;
    }

    @GetMapping("/addOrders")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOrdersToDb() {
        Faker faker = new Faker();
        List<UserDto> allUsers = userService.getAll();
        List<CertificateDto> allCertificates = giftCertificateService.getAll();
        for (int i = 0; i < 100; i++) {
            try {
                orderService.createOrder(createFakeOrder(allUsers, allCertificates, faker));
                LOGGER.info(i + " order created");
            } catch (Exception exception) {
                LOGGER.warn(i + " " + exception.getMessage());
                i--;
            }
        }
    }

    private OrderDto createFakeOrder(List<UserDto> allUsers, List<CertificateDto> allCertificates, Faker faker) {
        OrderDto order = new OrderDto();
        order.setUser(allUsers.get(faker.number().numberBetween(0, allUsers.size() - 1)));
        order.setCertificate(allCertificates.get(faker.number().numberBetween(0, allUsers.size() - 1)));
        return order;
    }
}
