package com.epam.esm.validator.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OrderValidatorImplTest {

    private static final CertificateDto CERTIFICATE_DTO = new CertificateDto(1L, "first", "for men",
            new BigDecimal("128.01"), 11, LocalDateTime.now(), LocalDateTime.now(), Collections.emptySet());
    private static final UserDto USER_DTO = new UserDto(1, "first");
    private static final OrderDto ORDER = new OrderDto(1L, LocalDateTime.now(), new BigDecimal("100"),
            CERTIFICATE_DTO, USER_DTO);
    @Mock
    private UserDao userDao;
    @Mock
    private GiftCertificateDao certificateDao;
    @InjectMocks
    private OrderValidatorImpl validator;

    @Test
    public void TestValidateOrderShouldThrowExceptionIfCertificateNotExist() {
        //when
        when(certificateDao.getById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(EntityNotFoundException.class, () -> validator.validateOrder(ORDER));
    }

    @Test
    public void TestValidateOrderShouldThrowExceptionIfUserNotExist() {
        //when
        when(certificateDao.getById(anyLong())).thenReturn((Optional.of(new GiftCertificate())));
        when(userDao.getById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(EntityNotFoundException.class, () -> validator.validateOrder(ORDER));
    }
}
