package com.checkmate.rules;

import com.checkmate.rules.email.EmailFormatRule;

public class EmailRule extends EmailFormatRule {
    public EmailRule() {
        super();
    }

    public EmailRule(String message) {
        super(message);
    }
}

