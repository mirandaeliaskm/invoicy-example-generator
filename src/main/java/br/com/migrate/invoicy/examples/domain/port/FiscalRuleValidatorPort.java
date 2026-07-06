package br.com.migrate.invoicy.examples.domain.port;

import br.com.migrate.invoicy.examples.domain.validation.ValidationResult;

import java.util.List;
import java.util.Map;

public interface FiscalRuleValidatorPort {
    ValidationResult validate(Map<String, Object> documentContext, List<String> ruleSetPaths);
}
