package com.epam.esm.modelcreator;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.OrderDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderCollectionModelCreator {
    public CollectionModel<OrderDto> createModel(List<OrderDto> allOrders, Long userId) {
        allOrders.forEach(this::addLinkWithItSelf);
        allOrders.forEach(this::addLinkUser);
        return CollectionModel.of(allOrders, linkTo(methodOn(UserController.class).
                getAllUserOrders(userId)).withSelfRel());
    }

    private void addLinkWithItSelf(OrderDto order) {
        order.add(linkTo(methodOn(UserController.class).
                getUserOrder( order.getUser().getId(),order.getId())).withSelfRel());
    }

    private void addLinkUser(OrderDto order) {
        order.add(linkTo(methodOn(UserController.class).getById(order.getUser().getId())).withRel("user"));
    }
}