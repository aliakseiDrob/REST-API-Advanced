package com.epam.esm.dao.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestConfig.class)
@Transactional
public class OrderDaoImplTest {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final User USER_INSTANCE = new User(1L, "Ivan");
    private static final List<Order> USER_ORDERS =
            Arrays.asList(new Order(1L, LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER),
                            new BigDecimal("340.00"), USER_INSTANCE),
                    new Order(2L, LocalDateTime.parse("2021-03-30 20:11:10", DATE_TIME_FORMATTER),
                            new BigDecimal("100.00"), USER_INSTANCE));

    @Autowired
    OrderDao orderDao;

    @Test
    public void testGetAllUserOrdersShouldReturnAllUserOrders() {
        //then
        assertEquals(USER_ORDERS, orderDao.getAllUserOrders(1L));
    }

    @Test
    public void testGetUserOrderShouldReturnOrder() {
        //given
        Optional<Order> expectedOrder = Optional.of(USER_ORDERS.get(0));
        //then
        assertEquals(expectedOrder, orderDao.getUserOrder(1L, 1L));
    }

    @Test
    public void testGetRowsCountShouldReturnAmountRowsInDb() {
        //then
        assertEquals(3L, orderDao.getRowsCount());
    }

    @Test
    @Rollback
    public void testCreateOrderShouldCreateOrder() {
        //given
        User user = new User(1L, "Ivan");
        Order order = new Order(0, LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER),
                new BigDecimal("340.00"), user);
        GiftCertificate giftCertificate = new GiftCertificate(1L, "first", "for men",
                new BigDecimal("128.01"), 11, 1, LocalDateTime.now(), LocalDateTime.now());
        //then
        order.setCertificate(giftCertificate);
        assertEquals(order, orderDao.createOrder(order));

    }
}
