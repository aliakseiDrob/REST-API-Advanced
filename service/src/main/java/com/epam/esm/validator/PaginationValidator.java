package com.epam.esm.validator;

public interface PaginationValidator {
   void isPaginationPageExists(int page, int items,long rowsCount);
}
