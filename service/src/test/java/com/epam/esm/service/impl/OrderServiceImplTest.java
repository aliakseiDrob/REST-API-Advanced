package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.*;
import com.epam.esm.entity.GiftCertificate;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    private static final OrderDetailsDto ORDER_DETAILS_DTO = new OrderDetailsDto();
    private static List<Order> orders;
    private static List<OrderDto> ordersDto;

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
        //when
        when(dao.getAllUserOrders(anyLong())).thenReturn(orders);
        when(modelMapper.map(orders.get(0), OrderDto.class)).thenReturn(ordersDto.get(0));
        when(modelMapper.map(orders.get(1), OrderDto.class)).thenReturn(ordersDto.get(1));
        //then
        assertEquals(ordersDto, service.getAllUserOrders(anyLong()));
        verify(dao, times(1)).getAllUserOrders(anyLong());
        verify(modelMapper, times(orders.size())).map(any(), any());
    }

    @Test
    public void testGetUserOrderShouldReturnOrderDetailsDto() {
        //when
        when(dao.getUserOrder(anyLong(), anyLong())).thenReturn(Optional.of(orders.get(0)));
        when(modelMapper.map(orders.get(0), OrderDetailsDto.class)).thenReturn(ORDER_DETAILS_DTO);
        //then
        assertEquals(ORDER_DETAILS_DTO, service.getUserOrder(anyLong(), anyLong()));
        verify(dao, times(1)).getUserOrder(anyLong(), anyLong());
        verify(modelMapper, times(1)).map(any(), any());
    }

    @Test
    public void testGetUserOrderShouldThrowExceptionIfOrderNotExist() {
        //when
        when(dao.getUserOrder(anyLong(), anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(EntityNotFoundException.class, () -> service.getUserOrder(anyLong(), anyLong()));
        verify(dao, times(1)).getUserOrder(anyLong(), anyLong());
        verify(modelMapper, times(0)).map(any(), any());
    }

    @Test
    public void testCreateOrderShouldReturnSavedOrderId() {
        //when
        doNothing().when(orderValidator).validateOrder(any(OrderDto.class));
        when(modelMapper.map(ordersDto.get(0), Order.class)).thenReturn(orders.get(0));
        when(dao.createOrder(orders.get(0))).thenReturn(orders.get(0));
        //then
        assertEquals(1L, service.createOrder(ordersDto.get(0)));
        verify(dao, times(1)).createOrder(any(Order.class));
        verify(modelMapper, times(1)).map(any(), any());
    }

    @Test
    public void testCreateOrderShouldThrowExceptionIfOrderNotValid() {
        //when
        doThrow(EntityNotFoundException.class).when(orderValidator).validateOrder(ordersDto.get(0));
        //then
        assertThrows(EntityNotFoundException.class, () -> service.createOrder(ordersDto.get(0)));
        verify(dao, times(0)).createOrder(any(Order.class));
        verify(modelMapper, times(0)).map(any(), any());
    }

    @Test
    void testGetRowCountsShouldReturnCountRowsInTable() {
        //when
        when(dao.getRowsCount()).thenReturn(1L);
        //then
        assertEquals(service.getRowCounts(), 1L);
        verify(dao, times(1)).getRowsCount();
    }
}