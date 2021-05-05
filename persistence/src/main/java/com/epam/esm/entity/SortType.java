package com.epam.esm.entity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SortType {
    ASC, DESC;

    public static boolean isSortTypeValid(String sortType) {
        return getSortTypes().contains(sortType.trim().toUpperCase());
    }

    private static List<String> getSortTypes() {
        return Stream.of(SortType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}