package com.checkmate.rules.common;

import com.checkmate.core.Rule;

import java.util.Optional;
import java.util.regex.Pattern;

public class RegexRule implements Rule {
    private final Pattern pattern;
    private final String message;

    public RegexRule(String regex, String message) {
        this(Pattern.compile(regex), message);
    }

    public RegexRule(Pattern pattern, String message) {
        this.pattern = pattern;
        this.message = message;
    }

    @Override
    public Optional<String> validate(String value) {
        if (value == null || !pattern.matcher(value).matches()) {
            return Optional.of(message);
        }
        return Optional.empty();
    }
}

