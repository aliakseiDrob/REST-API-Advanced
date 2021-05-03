package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Interface for managing Tag entities
 */
public interface TagDao {

    /**
     * Finds all  Tags  from database
     *
     * @return list of Tags from database
     */
    List<Tag> getAll();

    /**
     * Returns list of Tags starting from startPos position in the table
     *
     * @param items    number of records from the table
     * @param startPos starting position for search in the table
     * @return List of Tags
     */
    List<Tag> getAll(int startPos, int items);

    /**
     * Finds Tag from database by id
     *
     * @param id Tag id
     * @return Tag entity
     */
    Optional<Tag> getById(Long id);

    /**
     * finds amount rows in database
     *
     * @return amount rows in database
     */
    long getRowsCount();

    /**
     * Saves  Tag in database
     *
     * @param tag Tag entity
     * @return Tag id
     */
    long save(Tag tag);

    /**
     * Removes  Tag from database
     *
     * @param id Tag id
     */
    void delete(Long id);

    /**
     * Saves  set of Tags in database
     *
     * @param tags set of Tags
     * @return set of Tags saved in database
     */
    Set<Tag> saveTags(Set<Tag> tags);
}
