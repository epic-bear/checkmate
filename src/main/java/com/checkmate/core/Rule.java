package com.checkmate.core;

import java.util.Optional;

public interface Rule {
    Optional<String> validate(String value);
}

