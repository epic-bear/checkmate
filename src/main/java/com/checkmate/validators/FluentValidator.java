package com.checkmate.validators;

import com.checkmate.core.AbstractValidator;
import com.checkmate.rules.date.FutureDateRule;
import com.checkmate.rules.date.PastDateRule;
import com.checkmate.rules.email.EmailFormatRule;
import com.checkmate.rules.password.HasDigitRule;
import com.checkmate.rules.password.HasSpecialCharRule;
import com.checkmate.rules.password.HasUpperCaseRule;

public class FluentValidator extends AbstractValidator<FluentValidator> {
    public FluentValidator(String value) {
        super(value);
    }

    @Override
    protected FluentValidator self() {
        return this;
    }

    public FluentValidator isEmail() {
        return withRule(new EmailFormatRule());
    }

    public FluentValidator isPhone() {
        return matches("^\\+?[0-9]{10,15}$", "must be a valid phone number");
    }

    public FluentValidator isStrongPassword() {
        return minLength(8)
                .withRule(new HasDigitRule())
                .withRule(new HasUpperCaseRule())
                .withRule(new HasSpecialCharRule());
    }

    public FluentValidator isPastDate() {
        return withRule(new PastDateRule());
    }

    public FluentValidator isFutureDate() {
        return withRule(new FutureDateRule());
    }
}

