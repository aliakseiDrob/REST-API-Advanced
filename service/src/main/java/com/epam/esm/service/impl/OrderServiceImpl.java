package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.OrderDetailsDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.OrderService;
import com.epam.esm.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final OrderValidator orderValidator;
    private final ModelMapper modelMapper;

    @Override
    public List<OrderDto> getAllUserOrders(Long id) {
        List<Order> orders = orderDao.getAllUserOrders(id);
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailsDto getUserOrder(Long userId, Long orderId) {
        Order order = orderDao.getUserOrder(userId, orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found", 40404));
        return modelMapper.map(order, OrderDetailsDto.class);
    }

    @Override
    @Transactional
    public long createOrder(OrderDto order) {
        orderValidator.validateOrder(order);
        Order savedOrder = orderDao.createOrder(modelMapper.map(order, Order.class));
        return savedOrder.getId();
    }

    @Override
    public long getRowCounts() {
        return orderDao.getRowsCount();
    }

}
