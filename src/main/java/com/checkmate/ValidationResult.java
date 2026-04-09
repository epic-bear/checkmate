package com.checkmate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ValidationResult {
    private final Map<String, List<String>> fieldErrors = new LinkedHashMap<>();

    public void addError(String field, String message) {
        fieldErrors.computeIfAbsent(field, key -> new ArrayList<>()).add(message);
    }

    public boolean isValid() {
        return fieldErrors.isEmpty();
    }

    public Map<String, List<String>> getFieldErrors() {
        Map<String, List<String>> copy = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : fieldErrors.entrySet()) {
            copy.put(entry.getKey(), List.copyOf(entry.getValue()));
        }
        return Collections.unmodifiableMap(copy);
    }

    public List<String> getAllErrors() {
        List<String> all = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : fieldErrors.entrySet()) {
            for (String message : entry.getValue()) {
                all.add(entry.getKey() + ": " + message);
            }
        }
        return Collections.unmodifiableList(all);
    }
}

