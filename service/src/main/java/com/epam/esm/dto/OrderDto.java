package com.epam.esm.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends RepresentationModel<OrderDto> implements Serializable {
    private long id;
    private LocalDateTime orderDate;
    private BigDecimal orderCost;
    private CertificateDto certificate;
    private UserDto user;

}
