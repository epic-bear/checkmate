package com.checkmate.rules.date;

import com.checkmate.core.Rule;
import com.checkmate.utils.DateParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Passes when the value represents a date strictly before today.
 * By default any known date pattern is accepted; pass a specific
 * {@link DateTimeFormatter} or pattern string to restrict parsing.
 */
public class PastDateRule implements Rule {

    private final DateTimeFormatter formatter; // null = any known pattern
    private final String message;

    public PastDateRule() {
        this(null, "must be a past date");
    }

    public PastDateRule(String message) {
        this(null, message);
    }

    public PastDateRule(DateTimeFormatter formatter) {
        this(formatter, "must be a past date");
    }

    public PastDateRule(DateTimeFormatter formatter, String message) {
        this.formatter = formatter;
        this.message = message;
    }

    @Override
    public Optional<String> validate(String value) {
        Optional<LocalDate> parsed = formatter == null
                ? DateParser.tryParse(value)
                : DateParser.tryParse(value, formatter);

        if (parsed.isEmpty()) return Optional.of(message);
        return parsed.get().isBefore(LocalDate.now()) ? Optional.empty() : Optional.of(message);
    }
}

