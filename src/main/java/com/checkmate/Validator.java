package com.checkmate;

import com.checkmate.rules.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Validator {
    private final Map<String, List<Rule>> rulesByField = new HashMap<>();

    public Validator addRule(String field, Rule rule) {
        rulesByField.computeIfAbsent(field, key -> new ArrayList<>()).add(rule);
        return this;
    }

    public ValidationResult validate(Map<String, String> payload) {
        ValidationResult result = new ValidationResult();

        for (Map.Entry<String, List<Rule>> entry : rulesByField.entrySet()) {
            String field = entry.getKey();
            String value = payload.get(field);

            for (Rule rule : entry.getValue()) {
                Optional<String> error = rule.validate(value);
                error.ifPresent(message -> result.addError(field, message));
            }
        }

        return result;
    }
}

