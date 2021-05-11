package com.epam.esm.validator.impl;

import com.epam.esm.exception.PaginationPageException;
import com.epam.esm.validator.PaginationValidator;
import org.springframework.stereotype.Component;

@Component
public class PaginationValidatorImpl implements PaginationValidator {
    @Override
    public void validatePaginationPage(int page, int items, long rowsCount) {
        if (page < 1) {
            throw new PaginationPageException("Incorrect page number", 40014);
        }
        if (items < 1) {
            throw new PaginationPageException("Incorrect items number", 40015);
        }
        if ((page - 1) * items > rowsCount) {
            throw new PaginationPageException("Page not exists", 40405);
        }
    }
}
