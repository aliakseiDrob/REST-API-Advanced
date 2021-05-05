package com.epam.esm.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {
    private LocalDateTime orderDate;
    private BigDecimal orderCost;
}
