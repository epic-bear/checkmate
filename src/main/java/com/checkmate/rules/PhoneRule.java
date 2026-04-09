package com.checkmate.rules;

import com.checkmate.utils.RegexUtils;

import java.util.Optional;

public class PhoneRule implements Rule {
    private final String message;

    public PhoneRule() {
        this("must be a valid phone number");
    }

    public PhoneRule(String message) {
        this.message = message;
    }

    @Override
    public Optional<String> validate(String value) {
        if (!RegexUtils.matches(RegexUtils.PHONE_PATTERN, value)) {
            return Optional.of(message);
        }
        return Optional.empty();
    }
}

