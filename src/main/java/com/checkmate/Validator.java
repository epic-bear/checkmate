package com.checkmate;

import com.checkmate.core.Rule;
import com.checkmate.core.ValidationResult;
import com.checkmate.validators.DateValidator;
import com.checkmate.validators.FluentValidator;
import com.checkmate.validators.PasswordValidator;
import com.checkmate.validators.EmailValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Validator {
    private final Map<String, List<Rule>> rulesByField = new HashMap<>();

    public static FluentValidator of(String value) {
        return Validators.of(value);
    }

    public static EmailValidator email(String value) {
        return Validators.email(value);
    }

    public static PasswordValidator password(String value) {
        return Validators.password(value);
    }

    public static DateValidator date(String value) {
        return Validators.date(value);
    }

    public Validator addRule(String field, Rule rule) {
        rulesByField.computeIfAbsent(field, key -> new ArrayList<>()).add(rule);
        return this;
    }

    public ValidationResult validate(Map<String, String> payload) {
        ValidationResult result = new ValidationResult();
        Map<String, String> safePayload = payload == null ? Map.of() : payload;

        for (Map.Entry<String, List<Rule>> entry : rulesByField.entrySet()) {
            String field = entry.getKey();
            String value = safePayload.get(field);

            for (Rule rule : entry.getValue()) {
                Optional<String> error = rule.validate(value);
                error.ifPresent(message -> result.addError(field, message));
            }
        }

        return result;
    }
}

