package com.checkmate;

import com.checkmate.rules.date.DateFormatRule;
import com.checkmate.rules.date.PastDateRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateValidatorTest {

    // ── any known pattern ──────────────────────────────────────────────────────

    @ParameterizedTest(name = "[{index}] \"{0}\" is a valid date")
    @ValueSource(strings = {
            "2026-04-10",          // yyyy-MM-dd  (ISO)
            "2026-Apr-10",         // yyyy-MMM-dd
            "10-04-2026",          // dd-MM-yyyy
            "10/04/2026",          // dd/MM/yyyy
            "04/10/2026",          // MM/dd/yyyy
            "10.04.2026",          // dd.MM.yyyy
            "10 Apr 2026",         // dd MMM yyyy
            "Apr 10, 2026",        // MMM dd, yyyy
            "April 10, 2026",      // MMMM dd, yyyy
            "10 April 2026"        // dd MMMM yyyy
    })
    void shouldAcceptAnyKnownDatePattern(String date) {
        assertTrue(Validators.date(date).hasDateFormat().validate(), date);
    }

    @ParameterizedTest(name = "[{index}] \"{0}\" is NOT a valid date")
    @ValueSource(strings = {
            "not-a-date",
            "2026-13-01",          // invalid month
            "10:04:2026",          // colons
            ""
    })
    void shouldRejectInvalidDateStrings(String date) {
        assertFalse(Validators.date(date).hasDateFormat().validate(), date);
    }

    // ── specific pattern ───────────────────────────────────────────────────────

    @Test
    void shouldPassWhenPatternMatchesExactly() {
        assertTrue(Validators.date("10/04/2026").hasDateFormat("dd/MM/yyyy").validate());
    }

    @Test
    void shouldFailWhenPatternDoesNotMatch() {
        // ISO string vs dd/MM/yyyy required
        assertFalse(Validators.date("2026-04-10").hasDateFormat("dd/MM/yyyy").validate());
    }

    @Test
    void shouldAcceptCaseInsensitiveMonthAbbreviation() {
        assertTrue(Validators.date("10 apr 2026").hasDateFormat("dd MMM yyyy").validate());
    }

    // ── past / future with any pattern ────────────────────────────────────────

    @ParameterizedTest(name = "[{index}] past date \"{0}\" in any format")
    @ValueSource(strings = {
            "2000-01-01",
            "01 Jan 2000",
            "01/01/2000",
            "January 01, 2000"
    })
    void shouldPassPastDateForAnyKnownFormat(String date) {
        assertTrue(Validators.date(date).isPastDate().validate(), date);
    }

    @Test
    void shouldFailPastDateForFutureValue() {
        assertFalse(Validators.date("2099-12-31").isPastDate().validate());
    }

    @Test
    void shouldPassFutureDateForFutureValue() {
        assertTrue(Validators.date("2099-01-01").isFutureDate().validate());
    }

    @Test
    void shouldFailFutureDateForPastValue() {
        assertFalse(Validators.date("2000-01-01").isFutureDate().validate());
    }

    // ── past / future with specific pattern ───────────────────────────────────

    @Test
    void shouldPassPastDateWithSpecificPattern() {
        assertTrue(Validators.date("01/01/2000").isPastDate("dd/MM/yyyy").validate());
    }

    @Test
    void shouldFailPastDateWhenPatternMismatch() {
        // correct past date but wrong pattern supplied
        assertFalse(Validators.date("2000-01-01").isPastDate("dd/MM/yyyy").validate());
    }

    @Test
    void shouldPassFutureDateWithSpecificPattern() {
        assertTrue(Validators.date("31/12/2099").isFutureDate("dd/MM/yyyy").validate());
    }

    // ── direct rule usage (custom rules) ──────────────────────────────────────

    @Test
    void shouldUseRuleDirectlyInMapBasedValidator() {
        Validator v = new Validator()
                .addRule("dob", new DateFormatRule("dd/MM/yyyy"))
                .addRule("dob", new PastDateRule());

        assertTrue(v.validate(java.util.Map.of("dob", "01/01/2000")).isValid());
        assertFalse(v.validate(java.util.Map.of("dob", "2000-01-01")).isValid()); // wrong pattern for format rule
    }

    @Test
    void shouldCombineFormatAndFutureRule() {
        boolean valid = Validators.date("31/12/2099")
                .hasDateFormat("dd/MM/yyyy")
                .isFutureDate("dd/MM/yyyy")
                .validate();

        assertTrue(valid);
    }

    @Test
    void shouldFailCombinedFormatAndFutureRuleWhenPast() {
        boolean valid = Validators.date("01/01/2000")
                .hasDateFormat("dd/MM/yyyy")
                .isFutureDate("dd/MM/yyyy")
                .validate();

        assertFalse(valid);
    }
}

