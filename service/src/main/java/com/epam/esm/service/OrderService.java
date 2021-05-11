package com.epam.esm.service;

import com.epam.esm.dto.OrderDetailsDto;
import com.epam.esm.dto.OrderDto;

import java.util.List;

/**
 * Interface for serving Order objects according to the business logic of Order
 */
public interface OrderService {

    /**
     * Finds all User OrdersDto
     *
     * @param id User id
     * @return list of OrdersDto
     */
    List<OrderDto> getAllUserOrders(Long id);

    /**
     * Finds OrdersDto of UserDto by id
     *
     * @param userId  UserDto id
     * @param orderId OrderDto id
     * @return OrderDto
     */
    OrderDetailsDto getUserOrder(Long userId, Long orderId);

    /**
     * Saves  OrdersDto
     *
     * @param order OrderDto entity
     * @return OrderDto id
     */
    long createOrder(OrderDto order);

    /**
     * finds amount rows in database
     *
     * @return amount rows in database
     */
    long getRowCounts();

}