# CheckMate

CheckMate is a small Java validation library with fluent validators, reusable rules, and multi-error results.

## MVP features

- Fluent API
- Specialized validators (`EmailValidator`, `PasswordValidator`, `DateValidator`)
- Multiple errors per value
- Custom rules (`withRule(...)`)
- Entry-point factory (`Validators`)

## Project structure

- `src/main/java/com/checkmate/core/`
- `src/main/java/com/checkmate/validators/`
- `src/main/java/com/checkmate/rules/common/`
- `src/main/java/com/checkmate/rules/email/`
- `src/main/java/com/checkmate/rules/password/`
- `src/main/java/com/checkmate/rules/date/`
- `src/main/java/com/checkmate/Validators.java`

## Requirements

- Java 17+
- Maven 3.9+

## Run tests

```bash
mvn test
```

## Quick examples

```java
boolean valid = Validator.of("user@example.com")
        .isEmail()
        .isNotEmpty()
        .maxLength(50)
        .validate();
```

```java
boolean strongPassword = Validators.password("Strong!123")
        .isNotEmpty()
        .isStrongPassword()
        .maxLength(50)
        .validate();
```

