package com.checkmate.rules.common;

import com.checkmate.core.Rule;

import java.util.Optional;

public class MaxLengthRule implements Rule {
    private final int maxLength;
    private final String message;

    public MaxLengthRule(int maxLength) {
        this(maxLength, "must have at most " + maxLength + " characters");
    }

    public MaxLengthRule(int maxLength, String message) {
        this.maxLength = maxLength;
        this.message = message;
    }

    @Override
    public Optional<String> validate(String value) {
        if (value != null && value.length() > maxLength) {
            return Optional.of(message);
        }
        return Optional.empty();
    }
}

