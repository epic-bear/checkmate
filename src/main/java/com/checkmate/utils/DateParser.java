package com.checkmate.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Tries to parse a string into a {@link LocalDate} using a set of known date-only
 * patterns (no time component).
 *
 * <p>Supported patterns (case-insensitive for month abbreviations where applicable):
 * <ul>
 *   <li>{@code yyyy-MM-dd}       — e.g. 2026-04-10  (ISO)</li>
 *   <li>{@code yyyy-MMM-dd}      — e.g. 2026-Apr-10</li>
 *   <li>{@code dd-MM-yyyy}       — e.g. 10-04-2026</li>
 *   <li>{@code dd/MM/yyyy}       — e.g. 10/04/2026</li>
 *   <li>{@code MM/dd/yyyy}       — e.g. 04/10/2026</li>
 *   <li>{@code dd.MM.yyyy}       — e.g. 10.04.2026</li>
 *   <li>{@code dd MMM yyyy}      — e.g. 10 Apr 2026</li>
 *   <li>{@code MMM dd, yyyy}     — e.g. Apr 10, 2026</li>
 *   <li>{@code MMMM dd, yyyy}    — e.g. April 10, 2026</li>
 *   <li>{@code dd MMMM yyyy}     — e.g. 10 April 2026</li>
 * </ul>
 */
public final class DateParser {

    /** All supported date-only formatters, in priority order. */
    public static final List<DateTimeFormatter> KNOWN_FORMATTERS = List.of(
            // ISO  yyyy-MM-dd
            DateTimeFormatter.ISO_LOCAL_DATE,

            // yyyy-MMM-dd  (Apr)
            new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendPattern("yyyy-MMM-dd")
                    .toFormatter(Locale.ENGLISH),

            // dd-MM-yyyy
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),

            // dd/MM/yyyy
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),

            // MM/dd/yyyy
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),

            // dd.MM.yyyy
            DateTimeFormatter.ofPattern("dd.MM.yyyy"),

            // dd MMM yyyy  (10 Apr 2026)
            new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendPattern("dd MMM yyyy")
                    .toFormatter(Locale.ENGLISH),

            // MMM dd, yyyy  (Apr 10, 2026)
            new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendPattern("MMM dd, yyyy")
                    .toFormatter(Locale.ENGLISH),

            // MMMM dd, yyyy  (April 10, 2026)
            new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendPattern("MMMM dd, yyyy")
                    .toFormatter(Locale.ENGLISH),

            // dd MMMM yyyy  (10 April 2026)
            new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendPattern("dd MMMM yyyy")
                    .toFormatter(Locale.ENGLISH)
    );

    private DateParser() {
    }

    /**
     * Tries all known formatters and returns the first successful parse.
     */
    public static Optional<LocalDate> tryParse(String value) {
        if (value == null || value.isBlank()) return Optional.empty();
        for (DateTimeFormatter formatter : KNOWN_FORMATTERS) {
            try {
                return Optional.of(LocalDate.parse(value.trim(), formatter));
            } catch (DateTimeParseException ignored) {
                // try next formatter
            }
        }
        return Optional.empty();
    }

    /**
     * Parses with a specific formatter only.
     */
    public static Optional<LocalDate> tryParse(String value, DateTimeFormatter formatter) {
        if (value == null || value.isBlank()) return Optional.empty();
        try {
            return Optional.of(LocalDate.parse(value.trim(), formatter));
        } catch (DateTimeParseException ignored) {
            // value does not match the given formatter
            return Optional.empty();
        }
    }

    /**
     * Parses with a pattern string (convenience overload).
     */
    public static Optional<LocalDate> tryParse(String value, String pattern) {
        return tryParse(value, DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH));
    }
}

