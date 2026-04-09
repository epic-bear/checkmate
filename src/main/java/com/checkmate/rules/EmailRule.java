package com.checkmate.rules;

import com.checkmate.utils.RegexUtils;

import java.util.Optional;

public class EmailRule implements Rule {
    private final String message;

    public EmailRule() {
        this("must be a valid email address");
    }

    public EmailRule(String message) {
        this.message = message;
    }

    @Override
    public Optional<String> validate(String value) {
        if (!RegexUtils.matches(RegexUtils.EMAIL_PATTERN, value)) {
            return Optional.of(message);
        }
        return Optional.empty();
    }
}

