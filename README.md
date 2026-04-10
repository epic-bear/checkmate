# CheckMate

CheckMate is a small Java validation library with:

- fluent validation chains
- reusable rules
- specialized validators (email, password, date)
- multiple errors (not only first error)
- map-based object validation

## Requirements

- Java 17+
- Maven 3.9+

## Quick start

```bash
git clone <your-fork-or-repo-url>
cd checkmate
mvn test
```

## Core ideas

- `Validator.of("...")` or `Validators.of("...")` for generic fluent validation.
- `Validators.email(...)`, `Validators.password(...)`, `Validators.date(...)` for specialized validators.
- `validate()` returns `boolean`.
- `validateResult()` returns full `ValidationResult` with all collected messages.
- `new Validator().addRule("field", rule)` for map/payload validation by field.

## Fluent API examples

### Basic fluent chain

```java
boolean valid = Validator.of("user@example.com")
        .isEmail()
        .isNotEmpty()
        .maxLength(50)
        .validate();
```

### Collect multiple errors instead of first failure

```java
import com.checkmate.core.ValidationResult;

ValidationResult result = Validators.password("abc")
        .isStrongPassword() // min length + digit + uppercase + special char
        .validateResult();

if (!result.isValid()) {
    System.out.println(result.getAllErrors());
}
```

### Add a custom rule in chain

```java
import com.checkmate.rules.common.RegexRule;

ValidationResult result = Validators.of("A!1")
        .minLength(8)
        .withRule(new RegexRule("^[A-Za-z0-9!@#$%^&*]+$", "contains invalid symbols"))
        .validateResult();
```

## Specialized validators

### Email

```java
boolean ok = Validators.email("john.doe@example.com")
        .isNotEmpty()
        .isEmail()
        .maxLength(100)
        .validate();
```

### Password

```java
boolean strong = Validators.password("Strong!123")
        .isNotEmpty()
        .isStrongPassword()
        .validate();
```

You can also build rules step by step:

```java
boolean strong = Validators.password("Strong!123")
        .minLength(10)
        .hasUpperCase()
        .hasDigit()
        .hasSpecialChar()
        .validate();
```

### Phone (via generic fluent validator)

```java
boolean validPhone = Validators.of("+380501112233")
        .isPhone()
        .minLength(10)
        .maxLength(15)
        .validate();
```

## Date validation

Date validation is date-only in current MVP (no time part).

### 1) Accept any known date format

```java
boolean valid = Validators.date("10 Apr 2026")
        .hasDateFormat()
        .validate();
```

Known supported patterns:

- `yyyy-MM-dd` (ISO)
- `yyyy-MMM-dd`
- `dd-MM-yyyy`
- `dd/MM/yyyy`
- `MM/dd/yyyy`
- `dd.MM.yyyy`
- `dd MMM yyyy`
- `MMM dd, yyyy`
- `MMMM dd, yyyy`
- `dd MMMM yyyy`

### 2) Require one specific pattern

```java
boolean valid = Validators.date("10/04/2026")
        .hasDateFormat("dd/MM/yyyy")
        .validate();
```

### 3) Past and future checks

```java
boolean isPast = Validators.date("01/01/2000")
        .isPastDate("dd/MM/yyyy")
        .validate();

boolean isFuture = Validators.date("31/12/2099")
        .isFutureDate("dd/MM/yyyy")
        .validate();
```

### 4) Combine format + temporal rule

```java
boolean valid = Validators.date("31/12/2099")
        .hasDateFormat("dd/MM/yyyy")
        .isFutureDate("dd/MM/yyyy")
        .validate();
```

## Map-based validation (object/payload)

Use `Validator` when you validate a payload with several fields.

```java
import com.checkmate.Validator;
import com.checkmate.core.ValidationResult;
import com.checkmate.rules.EmailRule;
import com.checkmate.rules.PasswordRule;
import com.checkmate.rules.PhoneRule;
import com.checkmate.rules.common.NotEmptyRule;

Validator validator = new Validator()
        .addRule("email", new NotEmptyRule())
        .addRule("email", new EmailRule())
        .addRule("phone", new NotEmptyRule())
        .addRule("phone", new PhoneRule())
        .addRule("password", new NotEmptyRule())
        .addRule("password", new PasswordRule());

ValidationResult result = validator.validate(java.util.Map.of(
        "email", "wrong-email",
        "phone", "123",
        "password", "weak"
));

System.out.println(result.isValid()); // false
System.out.println(result.getFieldErrors());
System.out.println(result.getErrorsForField("email"));
System.out.println(result.getAllErrors());
```

## Custom rules

Any class that implements `com.checkmate.core.Rule` can be used in both APIs.

```java
import com.checkmate.core.Rule;

Rule noAdminEmail = value -> {
    if (value == null) {
        return java.util.Optional.of("email is required");
    }
    return value.endsWith("@admin.local")
            ? java.util.Optional.of("admin.local emails are not allowed")
            : java.util.Optional.empty();
};

boolean valid = Validators.email("root@admin.local")
        .withRule(noAdminEmail)
        .validate();
```

## Main packages

- `src/main/java/com/checkmate/core` - base validator infrastructure
- `src/main/java/com/checkmate/validators` - fluent and specialized validators
- `src/main/java/com/checkmate/rules/common` - generic reusable rules
- `src/main/java/com/checkmate/rules/email` - email-specific rules
- `src/main/java/com/checkmate/rules/password` - password-specific rules
- `src/main/java/com/checkmate/rules/date` - date format/past/future rules
- `src/test/java/com/checkmate` - usage-oriented tests

## Run tests

```bash
mvn test
```

