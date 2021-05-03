package com.epam.esm.dao;

import com.epam.esm.entity.MostWidelyUsedTag;
import com.epam.esm.entity.Order;

import java.util.List;
import java.util.Optional;

/**
 * Interface for managing GiftCertificate entities
 */
public interface OrderDao {
    /**
     * Finds all User Orders
     *
     * @param userId User id
     * @return list of Orders
     */
    List<Order> getAllUserOrders(Long userId);

    /**
     * Finds User Order by id
     *
     * @param userId  User id
     * @param orderId Order id
     * @return Order
     */
    Optional<Order> getUserOrder(long userId, long orderId);

    /**
     * Saves  Order in database
     *
     * @param order Order entity
     * @return Order
     */
    Order createOrder(Order order);

    /**
     * finds amount rows in database
     *
     * @return amount rows in database
     */
    long getRowsCount();

    /**
     * Finds the most widely used tag of a user with the highest cost of all orders
     *
     * @param userId User id
     * @return MostWidelyUsedTag entity witch contains the most widely used tag of a user
     * and the highest cost of all orders
     */
   MostWidelyUsedTag getMostWildlyUsedTag(Long userId);

}