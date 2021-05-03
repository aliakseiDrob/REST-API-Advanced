package com.epam.esm.modelcreator;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserCollectionModelCreator{

    private static final int FIRST_PAGE = 1;
    private static final int SECOND_PAGE = 2;
    private final UserService userService;

    @Autowired
    public UserCollectionModelCreator(UserService userService) {
        this.userService = userService;
    }

    public CollectionModel<UserDto> createModel(List<UserDto> users, int page, int items) {
        users.forEach(this::addLinkUserWithItself);
        users.forEach(this::addLinkUsersOrders);
        users.forEach(this::addLinkMostPopularTag);
        CollectionModel<UserDto> model = CollectionModel.of(users, linkTo(methodOn(UserController.class)
                .getAll(page, items)).withSelfRel());
        addPaginateLinks(model, page, items);
        return model;
    }

    private void addLinkUserWithItself(UserDto user) {
        user.add(linkTo(methodOn(UserController.class).getById(user.getId())).withSelfRel());
    }

    private void addLinkUsersOrders(UserDto user) {
        user.add(linkTo(methodOn(UserController.class).getAllUserOrders(user.getId())).withRel("orders"));
    }
    private void addLinkMostPopularTag(UserDto user){
        user.add(linkTo(methodOn(UserController.class).getMostWidelyUsedTag(user.getId())).withRel("mostPopularTag"));
    }

    private void addPaginateLinks(CollectionModel<UserDto> model, int page, int items) {
        addLinksPreviousPages(model, page, items);
        addLinkToNextPages(model, page, items);
    }

    private void addLinksPreviousPages(CollectionModel<UserDto> model, int page, int size) {
        if (page > FIRST_PAGE) {
            model.add(linkTo(methodOn(UserController.class).getAll(1, size)).withRel("firstPage"));
            if (page > SECOND_PAGE) {
                model.add(linkTo(methodOn(UserController.class).getAll(page - 1, size)).withRel("previousPage"));
            }
        }
    }

    private void addLinkToNextPages(CollectionModel<UserDto> model, int page, int items) {
        long usersQuantity = userService.getRowCounts();
        if (usersQuantity > page * items) {
            model.add(linkTo(methodOn(UserController.class).getAll(page + 1, items)).withRel("nextPage"));
            if (usersQuantity > (page+1) * items) {
                int lastPage = (int)Math.ceil(usersQuantity/(double)items);
                model.add(linkTo(methodOn(UserController.class).getAll(lastPage, items)).withRel("lastPage"));
            }
        }
    }
}
