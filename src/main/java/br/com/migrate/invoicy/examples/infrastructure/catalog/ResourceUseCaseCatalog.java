package br.com.migrate.invoicy.examples.infrastructure.catalog;

import br.com.migrate.invoicy.examples.domain.exception.UseCaseNotFoundException;
import br.com.migrate.invoicy.examples.domain.model.FiscalModule;
import br.com.migrate.invoicy.examples.domain.model.UseCaseCatalogFile;
import br.com.migrate.invoicy.examples.domain.model.UseCaseDefinition;
import br.com.migrate.invoicy.examples.domain.port.UseCaseCatalogPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ResourceUseCaseCatalog implements UseCaseCatalogPort {

    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory()).findAndRegisterModules();
    private final String catalogPath;
    private final Map<String, UseCaseDefinition> useCasesById = new ConcurrentHashMap<>();

    public ResourceUseCaseCatalog(@Value("${invoicy.examples.catalog:catalog/nfe-use-cases.yml}") String catalogPath) {
        this.catalogPath = catalogPath;
    }

    @PostConstruct
    public void loadCatalog() throws IOException {
        try (InputStream inputStream = new ClassPathResource(catalogPath).getInputStream()) {
            UseCaseCatalogFile catalogFile = yamlMapper.readValue(inputStream, UseCaseCatalogFile.class);
            useCasesById.clear();
            for (UseCaseDefinition useCase : catalogFile.useCases()) {
                useCasesById.put(useCase.id(), useCase);
            }
        }
    }

    @Override
    public List<UseCaseDefinition> listAll() {
        return useCasesById.values().stream()
                .sorted(Comparator.comparing(UseCaseDefinition::id))
                .toList();
    }

    @Override
    public List<UseCaseDefinition> listByModule(FiscalModule module) {
        return useCasesById.values().stream()
                .filter(useCase -> useCase.module() == module)
                .sorted(Comparator.comparing(UseCaseDefinition::id))
                .toList();
    }

    @Override
    public UseCaseDefinition getRequired(String useCaseId) {
        UseCaseDefinition useCaseDefinition = useCasesById.get(useCaseId);
        if (useCaseDefinition == null) {
            throw new UseCaseNotFoundException(useCaseId);
        }
        return useCaseDefinition;
    }
}
