package com.checkmate.core;

import com.checkmate.rules.common.MaxLengthRule;
import com.checkmate.rules.common.MinLengthRule;
import com.checkmate.rules.common.NotEmptyRule;
import com.checkmate.rules.common.RegexRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractValidator<T extends AbstractValidator<T>> {
    protected final String value;
    private final List<Rule> rules = new ArrayList<>();

    protected AbstractValidator(String value) {
        this.value = value;
    }

    protected abstract T self();

    public T withRule(Rule rule) {
        rules.add(rule);
        return self();
    }

    public T isNotEmpty() {
        return withRule(new NotEmptyRule());
    }

    public T minLength(int minLength) {
        return withRule(new MinLengthRule(minLength));
    }

    public T maxLength(int maxLength) {
        return withRule(new MaxLengthRule(maxLength));
    }

    public T matches(String regex, String message) {
        return withRule(new RegexRule(regex, message));
    }

    public ValidationResult validateResult() {
        ValidationResult result = new ValidationResult();
        for (Rule rule : rules) {
            Optional<String> error = rule.validate(value);
            error.ifPresent(result::addError);
        }
        return result;
    }

    public boolean validate() {
        return validateResult().isValid();
    }
}

