package br.com.migrate.invoicy.examples.infrastructure.rules;

import br.com.migrate.invoicy.examples.domain.port.FiscalRuleValidatorPort;
import br.com.migrate.invoicy.examples.domain.validation.Severity;
import br.com.migrate.invoicy.examples.domain.validation.ValidationMessage;
import br.com.migrate.invoicy.examples.domain.validation.ValidationResult;
import br.com.migrate.invoicy.examples.infrastructure.util.MapPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class YamlFiscalRuleValidator implements FiscalRuleValidatorPort {

    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory()).findAndRegisterModules();

    @Override
    public ValidationResult validate(Map<String, Object> documentContext, List<String> ruleSetPaths) {
        ValidationResult result = ValidationResult.empty();
        for (String ruleSetPath : ruleSetPaths) {
            RuleSetDefinition ruleSet = load(ruleSetPath);
            validateRuleSet(documentContext, ruleSet, result);
        }
        return result;
    }

    private RuleSetDefinition load(String ruleSetPath) {
        try (InputStream inputStream = new ClassPathResource(ruleSetPath).getInputStream()) {
            return yamlMapper.readValue(inputStream, RuleSetDefinition.class);
        } catch (IOException exception) {
            throw new IllegalStateException("Não foi possível carregar ruleset: " + ruleSetPath, exception);
        }
    }

    private void validateRuleSet(Map<String, Object> documentContext, RuleSetDefinition ruleSet, ValidationResult result) {
        for (RuleDefinition rule : ruleSet.rules()) {
            List<Object> values = MapPath.getValues(documentContext, rule.path());
            validateRequired(rule, values, result);
            validateAllowedValues(rule, values, result);
            validateFixedValue(rule, values, result);
            validateGreaterThanZero(rule, values, result);
        }
    }

    private void validateRequired(RuleDefinition rule, List<Object> values, ValidationResult result) {
        if (!rule.isRequired()) {
            return;
        }
        if (values.isEmpty() || values.stream().anyMatch(this::isBlank)) {
            addError(rule, result, defaultMessage(rule, "Campo obrigatório não informado."));
        }
    }

    private void validateAllowedValues(RuleDefinition rule, List<Object> values, ValidationResult result) {
        if (rule.allowedValues() == null || rule.allowedValues().isEmpty()) {
            return;
        }
        for (Object value : values) {
            if (!isBlank(value) && !rule.allowedValues().contains(value.toString())) {
                addError(rule, result, defaultMessage(rule, "Valor não permitido. Valores aceitos: " + rule.allowedValues()));
            }
        }
    }

    private void validateFixedValue(RuleDefinition rule, List<Object> values, ValidationResult result) {
        if (rule.fixedValue() == null) {
            return;
        }
        for (Object value : values) {
            if (!isBlank(value) && !rule.fixedValue().equals(value.toString())) {
                addError(rule, result, defaultMessage(rule, "Valor fixo esperado: " + rule.fixedValue()));
            }
        }
    }

    private void validateGreaterThanZero(RuleDefinition rule, List<Object> values, ValidationResult result) {
        if (!rule.isGreaterThanZero()) {
            return;
        }
        for (Object value : values) {
            if (isBlank(value) || new BigDecimal(value.toString()).compareTo(BigDecimal.ZERO) <= 0) {
                addError(rule, result, defaultMessage(rule, "Valor deve ser maior que zero."));
            }
        }
    }

    private boolean isBlank(Object value) {
        return value == null || value.toString().trim().isBlank();
    }

    private String defaultMessage(RuleDefinition rule, String fallback) {
        return rule.message() == null || rule.message().isBlank() ? fallback : rule.message();
    }

    private void addError(RuleDefinition rule, ValidationResult result, String message) {
        result.add(new ValidationMessage(Severity.ERROR, rule.id(), rule.path(), message));
    }
}
