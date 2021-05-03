package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.*;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.MostWidelyUsedTag;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.validator.OrderValidator;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static List<Order> orders;
    private static List<OrderDto> ordersDto;
    private static final CertificateDto CERTIFICATE_DTO = new CertificateDto(1L, "first", "for men",
            new BigDecimal("128.01"), 11,
            LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
            LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER), Collections.emptySet());
    private static final GiftCertificate GIFT_CERTIFICATE = new GiftCertificate(2L, "second", "for men",
            new BigDecimal("128.01"), 11, 1,
            LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
            LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER));
    private static final Order ORDER = new Order(1L, LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
            new BigDecimal("128.01"), new User(1L, "Ivan"), GIFT_CERTIFICATE);
    private static final OrderDto ORDER_DTO = new OrderDto(1L, LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
            new BigDecimal("128.01"), CERTIFICATE_DTO, new UserDto(1L, "Ivan"));
    private static final MostWidelyUsedTag MOST_WIDELY_USED_TAG =
            new MostWidelyUsedTag(1L, "Tag", new BigDecimal("100"));
    private static final MostUsedTagDto MOST_USED_TAG_DTO =
            new MostUsedTagDto(new TagDto(1L, "Tag"), new BigDecimal("100"));

    @Mock
    private OrderDao dao;
    @Mock
    private OrderValidator orderValidator;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    OrderServiceImpl service;

    @BeforeAll
    public static void init() {
        orders = Arrays.asList(new Order(1L, LocalDateTime.now(), new BigDecimal("100"), new User(), new GiftCertificate()),
                new Order(2L, LocalDateTime.now(), new BigDecimal("200"), new User(), new GiftCertificate()));
        ordersDto = Arrays.asList(new OrderDto(1L, LocalDateTime.now(), new BigDecimal("100"), new CertificateDto(), new UserDto()),
                new OrderDto(2L, LocalDateTime.now(), new BigDecimal("200"), new CertificateDto(), new UserDto()));
    }

    @Test
    public void testGetAllUserOrdersShouldReturnAllUsersOrders() {
        when(dao.getAllUserOrders(anyLong())).thenReturn(orders);
        when(modelMapper.map(orders.get(0), OrderDto.class)).thenReturn(ordersDto.get(0));
        when(modelMapper.map(orders.get(1), OrderDto.class)).thenReturn(ordersDto.get(1));
        assertEquals(service.getAllUserOrders(anyLong()), ordersDto);
        verify(dao, times(1)).getAllUserOrders(anyLong());
    }

    @Test
    public void testGetUserOrderShouldReturnOrder() {
        when(dao.getUserOrder(anyLong(), anyLong())).thenReturn(Optional.of(orders.get(0)));
        when(modelMapper.map(orders.get(0), OrderDto.class)).thenReturn(ordersDto.get(0));
        assertEquals(service.getUserOrder(anyLong(), anyLong()), ordersDto.get(0));
        verify(dao, times(1)).getUserOrder(anyLong(), anyLong());
    }

    @Test
    public void testGetUserOrderShouldThrowExceptionIfOrderNotExist() {
        when(dao.getUserOrder(anyLong(), anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getUserOrder(anyLong(), anyLong()));
        verify(dao, times(1)).getUserOrder(anyLong(), anyLong());
    }

    @Test
    public void testCreateOrderShouldReturnSavedOrderId() {
        doNothing().when(orderValidator).isOrderValid(any(OrderDto.class));
        when(modelMapper.map(ORDER_DTO, Order.class)).thenReturn(ORDER);
        when(dao.createOrder(ORDER)).thenReturn(ORDER);
        assertEquals(service.createOrder(ORDER_DTO), 1L);
    }

    @Test
    public void testCreateOrderShouldThrowExceptionIfOrderNotValid() {
        doThrow(EntityNotFoundException.class).when(orderValidator).isOrderValid(ORDER_DTO);
        assertThrows(EntityNotFoundException.class, () -> service.createOrder(ORDER_DTO));
    }

    @Test
    void testGetRowCountsShouldReturnCountRowsInTable() {
        when(dao.getRowsCount()).thenReturn(1L);
        assertEquals(service.getRowCounts(), 1L);
        verify(dao, times(1)).getRowsCount();
    }

    @Test
    public void testGetMostWidelyUsedTag() {
        when(dao.getMostWildlyUsedTag(1L)).thenReturn(MOST_WIDELY_USED_TAG);
        when(modelMapper.map(MOST_USED_TAG_DTO, MostUsedTagDto.class)).thenReturn(MOST_USED_TAG_DTO);
        assertEquals(service.getMostWidelyUsedTag(1L), MOST_USED_TAG_DTO);
    }
}