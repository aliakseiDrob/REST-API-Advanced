package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
public class MostUsedTagDto implements Serializable {
    private TagDto mostWidelyUsedTag;
    private BigDecimal highestOrderPrice;
}
