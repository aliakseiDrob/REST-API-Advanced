package com.epam.esm.validator;

import com.epam.esm.exception.PaginationPageException;

/**
 * Interface for validation Pagination
 */
public interface PaginationValidator {

    /**
     * Validates TagDto entity
     *
     * @param page      number of page
     * @param items     amount of elements on page
     * @param rowsCount amount of rows in database
     * @throws PaginationPageException if page not exists or parameters not valid
     **/
    void validatePaginationPage(int page, int items, long rowsCount);
}
