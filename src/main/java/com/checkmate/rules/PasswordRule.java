package com.checkmate.rules;

import com.checkmate.core.Rule;

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

        boolean valid = value.chars().anyMatch(Character::isUpperCase)
                && value.chars().anyMatch(Character::isLowerCase)
                && value.chars().anyMatch(Character::isDigit)
                && value.chars().anyMatch(c -> !Character.isLetterOrDigit(c));

        return valid ? Optional.empty() : Optional.of(message);
    }
}

