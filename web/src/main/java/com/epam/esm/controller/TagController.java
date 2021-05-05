package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.modelcreator.TagCollectionCreator;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The controller provides CRD operations on Tag entity.
 */
@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagCollectionCreator tagCollectionCreator;

    /**
     * Finds paginate page of TagDto
     *
     * @param page  required page
     * @param items number of TagDto on page
     * @return the list of TagDto
     */
    @GetMapping()
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
     */
    @GetMapping("/{id}")
    public TagDto getTag(@PathVariable Long id) {
        return tagService.getById(id);
    }

    /**
     * Saves TagDto
     *
     * @param tag TagDto entity
     * @return id the added TagDto
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public long save(@RequestBody TagDto tag) {
        return tagService.save(tag);
    }

    /**
     * Removes TagDto
     *
     * @param id the id of TagDto to remove
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        tagService.delete(id);
    }
}
