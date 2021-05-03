package com.epam.esm.validator;

import com.epam.esm.dto.OrderDto;

public interface OrderValidator {
    void isOrderValid(OrderDto order);
}
