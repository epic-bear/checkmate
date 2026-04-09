# CheckMate

CheckMate is a small Java validation library that applies reusable rules to incoming string payloads.

## Project structure

- `pom.xml`
- `src/main/java/com/checkmate/Validator.java`
- `src/main/java/com/checkmate/ValidationResult.java`
- `src/main/java/com/checkmate/rules/Rule.java`
- `src/main/java/com/checkmate/rules/EmailRule.java`
- `src/main/java/com/checkmate/rules/PhoneRule.java`
- `src/main/java/com/checkmate/rules/PasswordRule.java`
- `src/main/java/com/checkmate/rules/NotEmptyRule.java`
- `src/main/java/com/checkmate/utils/RegexUtils.java`
- `src/test/java/com/checkmate/ValidatorTest.java`

## Requirements

- Java 17+
- Maven 3.9+

## Run tests

```bash
mvn test
```

## Quick example

```java
Validator validator = new Validator()
        .addRule("email", new NotEmptyRule())
        .addRule("email", new EmailRule());

ValidationResult result = validator.validate(Map.of("email", "user@example.com"));
if (result.isValid()) {
    System.out.println("Valid payload");
} else {
    System.out.println(result.getAllErrors());
}
```

