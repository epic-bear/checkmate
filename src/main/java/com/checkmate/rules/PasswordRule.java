package com.checkmate.rules;

import java.util.Optional;

public class PasswordRule implements Rule {
    private final int minLength;
    private final String message;

    public PasswordRule() {
        this(8, "must contain at least 8 characters, including uppercase, lowercase, digit, and special character");
    }

    public PasswordRule(int minLength, String message) {
        this.minLength = minLength;
        this.message = message;
    }

    @Override
    public Optional<String> validate(String value) {
        if (value == null || value.length() < minLength) {
            return Optional.of(message);
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : value.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecial = true;
            }
        }

        if (hasUpper && hasLower && hasDigit && hasSpecial) {
            return Optional.empty();
        }
        return Optional.of(message);
    }
}

