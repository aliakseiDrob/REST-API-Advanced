package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.GlobalException;
import com.epam.esm.modelcreator.TagCollectionCreator;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The controller provides CRD operations on Tag entity.
 */
@RestController
public class TagController {

    private final TagService tagService;
    private final TagCollectionCreator tagCollectionCreator;


    @Autowired
    public TagController(TagService tagService, TagCollectionCreator tagCollectionCreator) {
        this.tagService = tagService;
        this.tagCollectionCreator = tagCollectionCreator;
    }

    /**
     * Finds paginate page of TagDto
     *
     * @param page  required page
     * @param items number of TagDto on page
     * @return the list of TagDto
     */
    @GetMapping("/tags")
    public CollectionModel<TagDto> getAll(@RequestParam(required = false, defaultValue = "1") int page,
                                          @RequestParam(required = false, defaultValue = "10") int items) {
        List<TagDto> tags = tagService.getAll(page, items);
        return tagCollectionCreator.createModel(tags, page, items);
    }

    /**
     * Finds TagDto by id
     *
     * @param id the id of TagDto entity
     * @return the TagDto with queried id
     * @throws GlobalException if TagDto doesn't exist
     */
    @GetMapping("/tags/{id}")
    public TagDto getTag(@PathVariable Long id) {
        return tagService.getById(id);
    }

    /**
     * Saves TagDto
     *
     * @param tag TagDto entity
     * @return id the added TagDto
     */
    @PostMapping("/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public long save(@RequestBody TagDto tag) {
        try {
            return tagService.save(tag);
        } catch (DataIntegrityViolationException exception) {
            throw new GlobalException("exception.message.40009", 40009, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Removes TagDto
     *
     * @param id the id of TagDto to remove
     */
    @DeleteMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        tagService.delete(id);
    }
}
