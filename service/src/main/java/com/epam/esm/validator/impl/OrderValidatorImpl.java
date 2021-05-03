package com.epam.esm.validator.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.validator.OrderValidator;
import org.springframework.stereotype.Component;

@Component
public class OrderValidatorImpl implements OrderValidator {
    private final UserDao userDao;
    private final GiftCertificateDao certificateDao;

    public OrderValidatorImpl(UserDao userDao, GiftCertificateDao certificateDao) {
        this.userDao = userDao;
        this.certificateDao = certificateDao;
    }

    @Override
    public void isOrderValid(OrderDto order) {
        isUserExists(order);
        isCertificateExists(order);
    }

    private void isCertificateExists(OrderDto order) {
        certificateDao.getById(order.getCertificate().getId())
                .orElseThrow(() -> new EntityNotFoundException("Certificate not found", 40401));
    }

    private void isUserExists(OrderDto order) {
        userDao.getById(order.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found", 40403));
    }
}
