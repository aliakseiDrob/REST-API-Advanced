package com.epam.esm.modelcreator;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class TagCollectionCreator {

    private static final int FIRST_PAGE = 1;
    private static final int SECOND_PAGE = 2;
    private final TagService tagService;

    public CollectionModel<TagDto> createModel(List<TagDto> tags, int page, int items) {
        tags.forEach(this::addLinkWithItself);
        CollectionModel<TagDto> model = CollectionModel.of(tags, linkTo(methodOn(TagController.class)
                .getAll(page, items)).withSelfRel());
        addPaginateLinks(model, page, items);
        return model;
    }

    private void addLinkWithItself(TagDto tag) {
        tag.add(linkTo(methodOn(TagController.class).getTag(tag.getId())).withSelfRel());
    }

    private void addPaginateLinks(CollectionModel<TagDto> model, int page, int items) {
        addLinksPreviousPages(model, page, items);
        addLinkToNextPages(model, page, items);
    }

    private void addLinksPreviousPages(CollectionModel<TagDto> model, int page, int size) {
        if (page > FIRST_PAGE) {
            model.add(linkTo(methodOn(TagController.class).getAll(1, size)).withRel("firstPage"));
            if (page > SECOND_PAGE) {
                model.add(linkTo(methodOn(TagController.class).getAll(page - 1, size)).withRel("previousPage"));
            }
        }
    }

    private void addLinkToNextPages(CollectionModel<TagDto> model, int page, int items) {
        long tagsQuantity = tagService.getRowCounts();
        if (tagsQuantity > page * items) {
            model.add(linkTo(methodOn(TagController.class).getAll(page + 1, items)).withRel("nextPage"));
            if (tagsQuantity > (page + 1) * items) {
                int lastPage = (int) Math.ceil(tagsQuantity / (double) items);
                model.add(linkTo(methodOn(TagController.class).getAll(lastPage, items)).withRel("lastPage"));
            }
        }
    }
}
