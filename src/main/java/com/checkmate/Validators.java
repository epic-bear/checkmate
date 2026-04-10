package com.checkmate;

import com.checkmate.validators.DateValidator;
import com.checkmate.validators.EmailValidator;
import com.checkmate.validators.FluentValidator;
import com.checkmate.validators.PasswordValidator;

public final class Validators {
    private Validators() {
    }

    public static FluentValidator of(String value) {
        return new FluentValidator(value);
    }

    public static EmailValidator email(String value) {
        return new EmailValidator(value);
    }

    public static PasswordValidator password(String value) {
        return new PasswordValidator(value);
    }

    public static DateValidator date(String value) {
        return new DateValidator(value);
    }
}

