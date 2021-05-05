package com.epam.esm.service;

import com.epam.esm.dto.MostUsedTagDto;
import com.epam.esm.dto.TagDto;

import java.util.List;

/**
 * Interface for serving TagDto objects according to the business logic of TagDto
 */
public interface TagService {

    /**
     * Returns all TagDto
     *
     * @return list of TagDto
     */
    List<TagDto> getAll();

    /**
     * Returns range of TagDto
     *
     * @param page  number of page
     * @param items amount of TagDto
     * @return list of TagDto
     */
    List<TagDto> getAll(int page, int items);

    /**
     * Returns TagDto by id
     *
     * @param id TagDto id
     * @return TagDto object
     */
    TagDto getById(long id);

    /**
     * finds amount rows in database
     *
     * @return amount rows in database
     */
    long getRowCounts();

    /**
     * Saves  TagDto
     *
     * @param tag TagDto entity
     * @return TagDto id
     */
    long save(TagDto tag);

    /**
     * Removes  TagDto
     *
     * @param id TagDto id
     */
    void delete(Long id);

    /**
     * Finds the most widely used tag of a user with the highest cost of all orders
     *
     * @param userId UserDto id
     * @return MostWidelyUsedTag entity witch contains the most widely used tag of a user
     * and the highest cost of all orders
     */
    MostUsedTagDto getMostWidelyUsedTag(Long userId);
}
