package com.epam.esm.controller;

import com.epam.esm.dto.MostUsedTagDto;
import com.epam.esm.dto.OrderDetailsDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.modelcreator.OrderCollectionModelCreator;
import com.epam.esm.modelcreator.UserCollectionModelCreator;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The controller provides CRD operations on UserDto entity and OrderDto entity.
 */

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final TagService tagService;
    private final UserCollectionModelCreator userCollectionModelCreator;
    private final OrderCollectionModelCreator orderCollectionModelCreator;

    /**
     * Finds UserDto by id
     *
     * @param id the id of UserDto entity
     * @return the UserDto with queried id
     */
    @GetMapping("/{id}")
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
    @GetMapping
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
    @GetMapping("/{id}/orders")
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
     */
    @GetMapping("/{userId}/orders/{orderId}")
    public OrderDetailsDto getUserOrder(@PathVariable Long userId, @PathVariable Long orderId) {
        return orderService.getUserOrder(userId, orderId);
    }

    /**
     * Creates OrderDto
     *
     * @param order OrderDto Entity
     * @return id created OrderDto Entity
     */
    @PostMapping("/{id}/orders")
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
    @GetMapping("/{userId}/mostUsedTag")
    public MostUsedTagDto getMostWidelyUsedTag(@PathVariable Long userId) {
        return tagService.getMostWidelyUsedTag(userId);
    }
}

