package com.epam.esm.service;

import com.epam.esm.dto.MostUsedTagDto;
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
    OrderDto getUserOrder(Long userId, Long orderId);

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

    /**
     * Finds the most widely used tag of a user with the highest cost of all orders
     *
     * @param userId UserDto id
     * @return MostWidelyUsedTag entity witch contains the most widely used tag of a user
     * and the highest cost of all orders
     */
    MostUsedTagDto getMostWidelyUsedTag(Long userId);

}