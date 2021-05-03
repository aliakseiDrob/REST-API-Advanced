package com.epam.esm.controller;

import com.epam.esm.dto.MostUsedTagDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.modelcreator.OrderCollectionModelCreator;
import com.epam.esm.modelcreator.UserCollectionModelCreator;
import com.epam.esm.exception.GlobalException;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The controller provides CRD operations on UserDto entity and OrderDto entity.
 */

@RestController
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final UserCollectionModelCreator userCollectionModelCreator;
    private final OrderCollectionModelCreator orderCollectionModelCreator;

    @Autowired
    public UserController(UserService userService, OrderService orderService,
                          UserCollectionModelCreator userCollectionModelCreator,
                          OrderCollectionModelCreator orderCollectionModelCreator) {
        this.userService = userService;
        this.orderService = orderService;
        this.userCollectionModelCreator = userCollectionModelCreator;
        this.orderCollectionModelCreator = orderCollectionModelCreator;
    }

    /**
     * Finds UserDto by id
     *
     * @param id the id of UserDto entity
     * @return the UserDto with queried id
     * @throws GlobalException if userDto doesn't exist
     */
    @GetMapping("/users/{id}")
    public UserDto getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    /**
     * Finds paginate page of usersDto
     *
     * @param page  required page
     * @param items number of usersDto on page
     * @return the list of usersDto
     */
    @GetMapping("/users")
    public CollectionModel<UserDto> getAll(@RequestParam(required = false, defaultValue = "1") int page,
                                           @RequestParam(required = false, defaultValue = "10") int items) {
        List<UserDto> users = userService.getAll(page, items);
        return userCollectionModelCreator.createModel(users, page, items);
    }

    /**
     * Finds all OrdersDto of UserDto
     *
     * @param id the id of UserDto entity
     * @return paginate page OrdersDto of UserDto
     */
    @GetMapping("users/{id}/orders")
    public CollectionModel<OrderDto> getAllUserOrders(@PathVariable Long id) {
        List<OrderDto> userOrders = orderService.getAllUserOrders(id);
        return orderCollectionModelCreator.createModel(userOrders, id);
    }

    /**
     * Finds UserDto OrdersDto by id
     *
     * @param userId  the id of UserDto entity
     * @param orderId the id of OrderDto entity
     * @return the OrderDto with queried id
     * @throws GlobalException if OrderDto doesn't exist
     */
    @GetMapping("users/{userId}/orders/{orderId}")
    public OrderDto getUserOrder(@PathVariable Long userId, @PathVariable Long orderId) {
        try {
            return orderService.getUserOrder(userId, orderId);
        } catch (EmptyResultDataAccessException ex) {
            throw new GlobalException("exception.message.40404", 40404, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates OrderDto
     *
     * @param order OrderDto Entity
     * @return id created OrderDto Entity
     */
    @PostMapping("users/{id}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public long createOrder(@RequestBody OrderDto order) {
        return orderService.createOrder(order);
    }

    /**
     * Finds the most widely used tag of a userDto with the highest cost of all ordersDto
     *
     * @param userId UserDto id
     * @return MostWidelyUsedTag entity witch contains the most widely used tagDto of a userDto
     * and the highest cost of all ordersDto
     */
    @GetMapping("/mostUsedTag/{userId}")
    public MostUsedTagDto getMostWidelyUsedTag(@PathVariable Long userId) {
        try {
            return orderService.getMostWidelyUsedTag(userId);
        } catch (EmptyResultDataAccessException ex) {
            throw new GlobalException("exception.message.40406", 40406, HttpStatus.NOT_FOUND);
        }
    }
}

