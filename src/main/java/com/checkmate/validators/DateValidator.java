package com.checkmate.validators;

import com.checkmate.core.AbstractValidator;
import com.checkmate.rules.date.DateFormatRule;
import com.checkmate.rules.date.FutureDateRule;
import com.checkmate.rules.date.PastDateRule;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class DateValidator extends AbstractValidator<DateValidator> {

    public DateValidator(String value) {
        super(value);
    }

    @Override
    protected DateValidator self() {
        return this;
    }

    // ── format checks ─────────────────────────────────────────────────────────

    /** Passes if the value matches any of the known date patterns. */
    public DateValidator hasDateFormat() {
        return withRule(new DateFormatRule());
    }

    /**
     * Passes only if the value matches {@code pattern}, e.g. {@code "dd/MM/yyyy"}.
     */
    public DateValidator hasDateFormat(String pattern) {
        return withRule(new DateFormatRule(pattern));
    }

    /**
     * Passes only if the value matches the given {@link DateTimeFormatter}.
     */
    public DateValidator hasDateFormat(DateTimeFormatter formatter) {
        return withRule(new DateFormatRule(formatter));
    }

    // ── temporal checks ───────────────────────────────────────────────────────

    /** Passes if the value is a past date (any known format). */
    public DateValidator isPastDate() {
        return withRule(new PastDateRule());
    }

    /**
     * Passes if the value is a past date parsed with the given {@code pattern}.
     */
    public DateValidator isPastDate(String pattern) {
        return withRule(new PastDateRule(formatterFor(pattern)));
    }

    /** Passes if the value is a future date (any known format). */
    public DateValidator isFutureDate() {
        return withRule(new FutureDateRule());
    }

    /**
     * Passes if the value is a future date parsed with the given {@code pattern}.
     */
    public DateValidator isFutureDate(String pattern) {
        return withRule(new FutureDateRule(formatterFor(pattern)));
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private static DateTimeFormatter formatterFor(String pattern) {
        return new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(pattern)
                .toFormatter(Locale.ENGLISH);
    }
}

