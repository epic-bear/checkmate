package com.checkmate;

import com.checkmate.core.ValidationResult;
import com.checkmate.rules.EmailRule;
import com.checkmate.rules.common.NotEmptyRule;
import com.checkmate.rules.PasswordRule;
import com.checkmate.rules.PhoneRule;
import com.checkmate.rules.common.RegexRule;
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

    @Test
    void shouldValidateEmailFluently() {
        boolean valid = Validator.of("user@example.com")
                .isEmail()
                .isNotEmpty()
                .maxLength(50)
                .validate();

        assertTrue(valid);
    }

    @Test
    void shouldFailEmailValidationForInvalidEmail() {
        boolean valid = Validators.email("not-an-email")
                .isEmail()
                .validate();

        assertFalse(valid);
    }

    @Test
    void shouldValidatePasswordWithMaxLength() {
        boolean valid = Validators.password("Strong!123")
                .isNotEmpty()
                .isStrongPassword()
                .maxLength(50)
                .validate();

        assertTrue(valid);
    }

    @Test
    void shouldFailPasswordValidationWhenTooLong() {
        String longPassword = "VeryStr0ng!Password".repeat(10);
        
        boolean valid = Validators.password(longPassword)
                .isNotEmpty()
                .isStrongPassword()
                .maxLength(50)
                .validate();

        assertFalse(valid);
    }

    @Test
    void shouldValidatePhoneWithFluentValidator() {
        boolean valid = Validators.of("+380501112233")
                .isPhone()
                .minLength(10)
                .maxLength(15)
                .validate();

        assertTrue(valid);
    }

    @Test
    void shouldValidateFutureDateWithSpecializedValidator() {
        boolean valid = Validators.date("2099-01-01")
                .isFutureDate()
                .validate();

        assertTrue(valid);
    }

    @Test
    void shouldCollectMultipleFluentErrors() {
        ValidationResult result = Validators.password("abc")
                .isStrongPassword()
                .withRule(new RegexRule("^[A-Za-z0-9!@#$%^&*]+$", "contains invalid symbols"))
                .validateResult();

        assertFalse(result.isValid());
        assertTrue(result.getErrorsForField("value").size() >= 2);
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

