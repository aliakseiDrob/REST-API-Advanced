package com.epam.esm.validator;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.EntityNotFoundException;

/**
 * Interface for validation OrderDto objects according to the business logic of OrderDto
 */
public interface OrderValidator {

    /**
     * Validates OrderDto entity
     *
     * @param order OrderDto entity
     * @throws EntityNotFoundException if UserDto or CertificateDto not exist
     **/
    void validateOrder(OrderDto order);
}
