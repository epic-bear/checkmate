package com.checkmate.rules;

import java.util.Optional;

public interface Rule {
    Optional<String> validate(String value);
}

