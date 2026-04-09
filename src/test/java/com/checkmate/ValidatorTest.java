package com.checkmate;

import com.checkmate.rules.EmailRule;
import com.checkmate.rules.NotEmptyRule;
import com.checkmate.rules.PasswordRule;
import com.checkmate.rules.PhoneRule;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

    @Test
    void shouldPassWhenAllValuesAreValid() {
        Validator validator = buildValidator();

        ValidationResult result = validator.validate(Map.of(
                "email", "user@example.com",
                "phone", "+380501112233",
                "password", "Strong!123",
                "name", "Yana"
        ));

        assertTrue(result.isValid());
        assertTrue(result.getAllErrors().isEmpty());
    }

    @Test
    void shouldCollectAllErrorsWhenValuesAreInvalid() {
        Validator validator = buildValidator();

        ValidationResult result = validator.validate(Map.of(
                "email", "wrong-email",
                "phone", "123",
                "password", "weak",
                "name", "   "
        ));

        assertFalse(result.isValid());
        assertTrue(result.getFieldErrors().containsKey("email"));
        assertTrue(result.getFieldErrors().containsKey("phone"));
        assertTrue(result.getFieldErrors().containsKey("password"));
        assertTrue(result.getFieldErrors().containsKey("name"));
    }

    @Test
    void shouldMarkMissingFieldsAsInvalid() {
        Validator validator = buildValidator();

        ValidationResult result = validator.validate(Map.of());

        assertFalse(result.isValid());
        assertTrue(result.getFieldErrors().containsKey("email"));
        assertTrue(result.getFieldErrors().containsKey("phone"));
        assertTrue(result.getFieldErrors().containsKey("password"));
        assertTrue(result.getFieldErrors().containsKey("name"));
    }

    private Validator buildValidator() {
        return new Validator()
                .addRule("email", new NotEmptyRule())
                .addRule("email", new EmailRule())
                .addRule("phone", new NotEmptyRule())
                .addRule("phone", new PhoneRule())
                .addRule("password", new NotEmptyRule())
                .addRule("password", new PasswordRule())
                .addRule("name", new NotEmptyRule("name is required"));
    }
}

