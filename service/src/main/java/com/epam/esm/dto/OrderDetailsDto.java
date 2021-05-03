package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
@Data
@Getter
@Setter
public class OrderDetailsDto {
    private long id;
    private LocalDateTime orderDate;
    private BigDecimal orderCost;
    private GiftCertificate certificate;
}
