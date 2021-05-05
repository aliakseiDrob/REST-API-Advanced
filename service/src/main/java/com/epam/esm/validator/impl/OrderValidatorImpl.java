package com.epam.esm.validator.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderValidatorImpl implements OrderValidator {
    private final UserDao userDao;
    private final GiftCertificateDao certificateDao;

    @Override
    public void validateOrder(OrderDto order) {
        validateCertificate(order);
        validateUser(order);
    }

    private void validateCertificate(OrderDto order) {
        certificateDao.getById(order.getCertificate().getId())
                .orElseThrow(() -> new EntityNotFoundException("Certificate not found", 40401));
    }

    private void validateUser(OrderDto order) {
        userDao.getById(order.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found", 40403));
    }
}
