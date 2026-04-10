package com.checkmate.rules.password;

import com.checkmate.core.Rule;

import java.util.Optional;

public class HasDigitRule implements Rule {
    private final String message;

    public HasDigitRule() {
        this("must contain at least one digit");
    }

    public HasDigitRule(String message) {
        this.message = message;
    }

    @Override
    public Optional<String> validate(String value) {
        if (value == null || value.chars().noneMatch(Character::isDigit)) {
            return Optional.of(message);
        }
        return Optional.empty();
    }
}

