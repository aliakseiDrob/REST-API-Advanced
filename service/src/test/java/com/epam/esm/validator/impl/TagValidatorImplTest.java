package com.epam.esm.validator.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.TagValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TagValidatorImplTest {

    private static final TagDto TAG_DTO_WITH_EMPTY_NAME = new TagDto(1L, "  ");
    private static final TagDto TAG_DTO_WITH_LONG_NAME = new TagDto(1L,
            "VERY_LONG_NAME_MORE_THEN_64_CHARACTERS_VERY_LONG_NAME_MORE_THEN_64_CHARACTERS");

   private final TagValidatorImpl tagValidator = new TagValidatorImpl();

    @Test
    public void testValidateTagShouldThrowExceptionIfTagNameEmpty() {
        //then
        assertThrows(TagValidationException.class, () -> tagValidator.validateTag(TAG_DTO_WITH_EMPTY_NAME));
    }

    @Test
    public void testValidateTagShouldThrowExceptionIfTagNameMoreThenExpected() {
        //then
        assertThrows(TagValidationException.class, () -> tagValidator.validateTag(TAG_DTO_WITH_LONG_NAME));
    }
}
