package com.checkmate.rules;

import java.util.Optional;

public class NotEmptyRule implements Rule {
    private final String message;

    public NotEmptyRule() {
        this("must not be empty");
    }

    public NotEmptyRule(String message) {
        this.message = message;
    }

    @Override
    public Optional<String> validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return Optional.of(message);
        }
        return Optional.empty();
    }
}

