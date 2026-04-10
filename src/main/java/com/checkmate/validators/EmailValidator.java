package com.checkmate.validators;

import com.checkmate.core.AbstractValidator;
import com.checkmate.rules.email.EmailFormatRule;

public class EmailValidator extends AbstractValidator<EmailValidator> {
    public EmailValidator(String value) {
        super(value);
    }

    @Override
    protected EmailValidator self() {
        return this;
    }

    public EmailValidator isEmail() {
        return withRule(new EmailFormatRule());
    }
}

