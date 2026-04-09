package com.checkmate.utils;

import java.util.regex.Pattern;

public final class RegexUtils {
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    public static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{10,15}$");

    private RegexUtils() {
    }

    public static boolean matches(Pattern pattern, String value) {
        return value != null && pattern.matcher(value).matches();
    }
}

