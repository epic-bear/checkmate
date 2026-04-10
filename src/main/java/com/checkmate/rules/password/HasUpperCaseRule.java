package com.checkmate.rules.password;

import com.checkmate.core.Rule;

import java.util.Optional;

public class HasUpperCaseRule implements Rule {
    private final String message;

    public HasUpperCaseRule() {
        this("must contain at least one uppercase letter");
    }

    public HasUpperCaseRule(String message) {
        this.message = message;
    }

    @Override
    public Optional<String> validate(String value) {
        if (value == null || value.chars().noneMatch(Character::isUpperCase)) {
            return Optional.of(message);
        }
        return Optional.empty();
    }
}

