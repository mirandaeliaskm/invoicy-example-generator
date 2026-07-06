package br.com.migrate.invoicy.examples.domain.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationResult {

    private final List<ValidationMessage> messages = new ArrayList<>();

    public static ValidationResult empty() {
        return new ValidationResult();
    }

    public void add(ValidationMessage message) {
        messages.add(message);
    }

    public void addAll(ValidationResult other) {
        messages.addAll(other.messages());
    }

    public List<ValidationMessage> messages() {
        return Collections.unmodifiableList(messages);
    }

    public boolean hasErrors() {
        return messages.stream().anyMatch(message -> message.severity() == Severity.ERROR);
    }

    public boolean isValid() {
        return !hasErrors();
    }
}
