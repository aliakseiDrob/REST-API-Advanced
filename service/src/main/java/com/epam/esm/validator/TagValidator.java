package com.epam.esm.validator;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.TagValidationException;

/**
 * Interface for validation TagDto objects according to the business logic of TagDto
 */
public interface TagValidator {

    /**
     * Validates TagDto entity
     *
     * @param tag TagDto entity
     * @throws TagValidationException if TagDto not valid
     **/
    void validateTag(TagDto tag);
}
