package com.checkmate.rules.password;

import com.checkmate.core.Rule;

import java.util.Optional;

public class HasSpecialCharRule implements Rule {
    private final String message;

    public HasSpecialCharRule() {
        this("must contain at least one special character");
    }

    public HasSpecialCharRule(String message) {
        this.message = message;
    }

    @Override
    public Optional<String> validate(String value) {
        if (value == null || value.chars().allMatch(Character::isLetterOrDigit)) {
            return Optional.of(message);
        }
        return Optional.empty();
    }
}

