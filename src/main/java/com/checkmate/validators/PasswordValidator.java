package com.checkmate.validators;

import com.checkmate.core.AbstractValidator;
import com.checkmate.rules.password.HasDigitRule;
import com.checkmate.rules.password.HasSpecialCharRule;
import com.checkmate.rules.password.HasUpperCaseRule;

public class PasswordValidator extends AbstractValidator<PasswordValidator> {
    public PasswordValidator(String value) {
        super(value);
    }

    @Override
    protected PasswordValidator self() {
        return this;
    }

    public PasswordValidator hasDigit() {
        return withRule(new HasDigitRule());
    }

    public PasswordValidator hasUpperCase() {
        return withRule(new HasUpperCaseRule());
    }

    public PasswordValidator hasSpecialChar() {
        return withRule(new HasSpecialCharRule());
    }

    public PasswordValidator isStrongPassword() {
        return minLength(8)
                .hasDigit()
                .hasUpperCase()
                .hasSpecialChar();
    }
}

