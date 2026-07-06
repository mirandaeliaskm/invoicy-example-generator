package br.com.migrate.invoicy.examples.application;

import br.com.migrate.invoicy.examples.domain.exception.FiscalValidationException;
import br.com.migrate.invoicy.examples.domain.model.GenerateExampleCommand;
import br.com.migrate.invoicy.examples.domain.model.GeneratedExample;
import br.com.migrate.invoicy.examples.domain.model.OutputFormat;
import br.com.migrate.invoicy.examples.domain.model.UseCaseDefinition;
import br.com.migrate.invoicy.examples.domain.port.FiscalRuleValidatorPort;
import br.com.migrate.invoicy.examples.domain.port.SampleDataPort;
import br.com.migrate.invoicy.examples.domain.port.TemplateRendererPort;
import br.com.migrate.invoicy.examples.domain.port.UseCaseCatalogPort;
import br.com.migrate.invoicy.examples.domain.validation.ValidationResult;
import br.com.migrate.invoicy.examples.infrastructure.util.DeepMapMerger;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class GenerateExampleService {

    private final UseCaseCatalogPort catalogPort;
    private final SampleDataPort sampleDataPort;
    private final TemplateRendererPort templateRendererPort;
    private final FiscalRuleValidatorPort ruleValidatorPort;
    private final FiscalCalculationService fiscalCalculationService;

    public GenerateExampleService(
            UseCaseCatalogPort catalogPort,
            SampleDataPort sampleDataPort,
            TemplateRendererPort templateRendererPort,
            FiscalRuleValidatorPort ruleValidatorPort,
            FiscalCalculationService fiscalCalculationService
    ) {
        this.catalogPort = catalogPort;
        this.sampleDataPort = sampleDataPort;
        this.templateRendererPort = templateRendererPort;
        this.ruleValidatorPort = ruleValidatorPort;
        this.fiscalCalculationService = fiscalCalculationService;
    }

    public GeneratedExample generate(GenerateExampleCommand command) {
        UseCaseDefinition useCase = catalogPort.getRequired(command.useCaseId());
        Map<String, Object> context = sampleDataPort.load(useCase.sampleData());

        DeepMapMerger.applyDotNotationOverrides(context, command.safeOverrides());
        fiscalCalculationService.calculate(context);

        ValidationResult validationResult = ruleValidatorPort.validate(context, useCase.ruleSets());
        if (validationResult.hasErrors()) {
            throw new FiscalValidationException(validationResult);
        }

        Map<OutputFormat, String> generatedFiles = new LinkedHashMap<>();
        for (OutputFormat outputFormat : command.outputFormats()) {
            generatedFiles.put(outputFormat, renderOutput(useCase, outputFormat, context));
        }

        return new GeneratedExample(
                UUID.randomUUID().toString(),
                OffsetDateTime.now(),
                useCase,
                generatedFiles,
                validationResult
        );
    }

    private String renderOutput(UseCaseDefinition useCase, OutputFormat outputFormat, Map<String, Object> context) {
        String templatePath = switch (outputFormat) {
            case XML -> useCase.template().xml();
            case JSON -> useCase.template().json();
            case README -> useCase.template().readme();
        };
        return templateRendererPort.render(templatePath, context);
    }
}
