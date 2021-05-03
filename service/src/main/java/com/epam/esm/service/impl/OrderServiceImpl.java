package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.MostUsedTagDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.MostWidelyUsedTag;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.OrderService;
import com.epam.esm.validator.OrderValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final OrderValidator orderValidator;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderDao orderDao, OrderValidator orderValidator, ModelMapper modelMapper) {
        this.orderDao = orderDao;
        this.orderValidator = orderValidator;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<OrderDto> getAllUserOrders(Long id) {
        List<Order> orders = orderDao.getAllUserOrders(id);
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getUserOrder(Long userId, Long orderId) {
        Order order = orderDao.getUserOrder(userId, orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found", 40404));
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    @Transactional
    public long createOrder(OrderDto order) {
        orderValidator.isOrderValid(order);
        Order savedOrder = orderDao.createOrder(modelMapper.map(order, Order.class));
        return savedOrder.getId();
    }

    @Override
    public long getRowCounts() {
        return orderDao.getRowsCount();
    }

    @Override
    public MostUsedTagDto getMostWidelyUsedTag(Long userId) {
        MostWidelyUsedTag mostWidelyUsedTag = orderDao.getMostWildlyUsedTag(userId);
        return modelMapper.map(mostWidelyUsedTag, MostUsedTagDto.class);
    }

}
