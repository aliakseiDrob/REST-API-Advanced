package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    /**
     * Finds User from database by id
     *
     * @param id User id
     * @return User entity
     */
    Optional<User> getById(Long id);

    /**
     * Returns list of Users starting from startPos position in the table
     *
     * @param items    number of records from the table
     * @param startPos starting position for search in the table
     * @return List of Users
     */
    List<User> getAll(int startPos, int items);

    /**
     * Finds all  Users  from database
     *
     * @return list of Users from database
     */
    List<User> getAll();

    /**
     * finds amount rows in database
     *
     * @return amount rows in database
     */
    long getRowsCount();

    /**
     * Saves  User in database
     *
     * @param user User entity
     */
    long save(User user);
}
