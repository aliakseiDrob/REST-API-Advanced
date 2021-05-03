package com.epam.esm.validator.impl;

import com.epam.esm.exception.PaginationPageException;
import com.epam.esm.validator.PaginationValidator;
import org.springframework.stereotype.Component;

@Component
public class PaginationValidatorImpl implements PaginationValidator {
    @Override
    public void isPaginationPageExists(int page, int items, long rowsCount) {
        if (!(isPaginationParametersValid(page, items) && (page - 1) * items < rowsCount)) {
            throw new PaginationPageException("Page not exists", 40405);
        }
    }

    private static boolean isPaginationParametersValid(int page, int items) {
        return page > 0 && items > 0;
    }
}
