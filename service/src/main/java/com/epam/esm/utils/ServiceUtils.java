package com.epam.esm.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Set;

public class ServiceUtils {

    public static boolean isParameterPassed(String param) {
        return StringUtils.isNoneBlank(param);
    }

    public static boolean isTagNamesPassed(Set<String> names) {
        return names != null && names.size() > 0;
    }

    public static int calculateStartPos(int page, int items) {
        return (page - 1) * items;
    }
}
