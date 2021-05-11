package com.epam.esm.entity;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class MostWidelyUsedTag{
    private final Tag mostWidelyUsedTag;
    private final BigDecimal highestOrderPrice;

    public MostWidelyUsedTag(long id, String name, BigDecimal highestOrderPrice) {
        mostWidelyUsedTag = new Tag(id, name);
        this.highestOrderPrice = highestOrderPrice;
    }
}
