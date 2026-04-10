package com.checkmate.rules.email;

import com.checkmate.rules.common.RegexRule;

import java.util.regex.Pattern;

public class EmailFormatRule extends RegexRule {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public EmailFormatRule() {
        this("must be a valid email address");
    }

    public EmailFormatRule(String message) {
        super(EMAIL_PATTERN, message);
    }
}

