package com.epam.esm.validator.impl;

import com.epam.esm.exception.PaginationPageException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaginationValidatorImplTest {

    private static final int VALID_PAGE_VALUE = 3;
    private static final int INVALID_PAGE_VALUE = -3;
    private static final int VALID_ITEMS_ON_PAGE_VALUE = 2;
    private static final int INVALID_ITEMS_ON_PAGE_VALUE = -2;
    private static final int ONE_ROW_COUNT = 1;
    private static final int TEN_ROWS_COUNT = 10;
    private final PaginationValidatorImpl paginationValidator = new PaginationValidatorImpl();

    @Test
    public void testValidatePaginationPageShouldThrowExceptionIfPageNotExist() {
        //then
        assertThrows(PaginationPageException.class,
                () -> paginationValidator.validatePaginationPage(VALID_PAGE_VALUE, VALID_ITEMS_ON_PAGE_VALUE, ONE_ROW_COUNT));
    }

    @Test
    public void testValidatePaginationPageShouldThrowExceptionIfPageParameterNotValid() {
        //then
        assertThrows(PaginationPageException.class,
                () -> paginationValidator.validatePaginationPage(INVALID_PAGE_VALUE, VALID_ITEMS_ON_PAGE_VALUE, TEN_ROWS_COUNT));
    }

    @Test
    public void testValidatePaginationPageShouldThrowExceptionIfItemsParameterNotValid() {
        //then
        assertThrows(PaginationPageException.class,
                () -> paginationValidator.validatePaginationPage(VALID_PAGE_VALUE, INVALID_ITEMS_ON_PAGE_VALUE, ONE_ROW_COUNT));
    }

    @Test
    public void testIsPaginationParametersValidShouldReturnTrueWhenParamValid() {
        //then
        assertTrue(paginationValidator.isPaginationParametersValid(VALID_PAGE_VALUE, VALID_ITEMS_ON_PAGE_VALUE));
    }

    @Test
    public void testIsPaginationParametersValidShouldReturnTrueWhenParamNotValid() {
        //then
        assertFalse(paginationValidator.isPaginationParametersValid(INVALID_PAGE_VALUE, INVALID_ITEMS_ON_PAGE_VALUE));
    }

}
