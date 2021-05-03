package com.epam.esm.service;

import com.epam.esm.dto.UserDto;

import java.util.List;

/**
 * Interface for serving User objects according to the business logic of User
 */
public interface UserService {
    /**
     * Returns UserDto by id
     *
     * @param id UserDto id
     * @return UserDto object
     */
    UserDto getById(Long id);

    /**
     * Returns range of UsersDto
     *
     * @param page  number of page
     * @param items amount of UsersDto
     * @return list of UsersDto
     */
    List<UserDto> getAll(int page, int items);

    /**
     * Returns all UsersDto
     *
     * @return list of UsersDto
     */
    List<UserDto> getAll();

    /**
     * finds amount rows in database
     *
     * @return amount rows in database
     */
    long getRowCounts();

    /**
     * Saves  UserDto
     *
     * @param user UserDto entity
     * @return UserDto id
     */
    long save(UserDto user);
}

