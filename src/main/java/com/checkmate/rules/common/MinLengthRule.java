package com.checkmate.rules.common;

import com.checkmate.core.Rule;

import java.util.Optional;

public class MinLengthRule implements Rule {
    private final int minLength;
    private final String message;

    public MinLengthRule(int minLength) {
        this(minLength, "must have at least " + minLength + " characters");
    }

    public MinLengthRule(int minLength, String message) {
        this.minLength = minLength;
        this.message = message;
    }

    @Override
    public Optional<String> validate(String value) {
        if (value == null || value.length() < minLength) {
            return Optional.of(message);
        }
        return Optional.empty();
    }
}

