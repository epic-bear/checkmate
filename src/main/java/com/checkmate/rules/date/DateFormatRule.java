package com.checkmate.rules.date;

import com.checkmate.core.Rule;
import com.checkmate.utils.DateParser;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.Optional;

/**
 * Validates that the value is a recognisable date string.
 *
 * <ul>
 *   <li>{@link #DateFormatRule()} — accepts any of the known date patterns.</li>
 *   <li>{@link #DateFormatRule(String)} — accepts only the supplied pattern string
 *       (e.g. {@code "dd/MM/yyyy"}).</li>
 *   <li>{@link #DateFormatRule(DateTimeFormatter)} — accepts only the supplied formatter.</li>
 * </ul>
 */
public class DateFormatRule implements Rule {

    private final DateTimeFormatter requiredFormatter; // null = any known pattern
    private final String message;

    /** Accept any of the known date patterns. */
    public DateFormatRule() {
        this(null, "must be a valid date");
    }

    /**
     * Accept only {@code pattern}, e.g. {@code "dd/MM/yyyy"}.
     * The message will name the required pattern.
     */
    public DateFormatRule(String pattern) {
        this(
                new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern(pattern)
                        .toFormatter(Locale.ENGLISH),
                "must be a valid date in format " + pattern
        );
    }

    /** Accept only {@code formatter}. */
    public DateFormatRule(DateTimeFormatter formatter) {
        this(formatter, "must be a valid date in the required format");
    }

    public DateFormatRule(DateTimeFormatter formatter, String message) {
        this.requiredFormatter = formatter;
        this.message = message;
    }

    @Override
    public Optional<String> validate(String value) {
        boolean valid = requiredFormatter == null
                ? DateParser.tryParse(value).isPresent()
                : DateParser.tryParse(value, requiredFormatter).isPresent();
        return valid ? Optional.empty() : Optional.of(message);
    }
}

