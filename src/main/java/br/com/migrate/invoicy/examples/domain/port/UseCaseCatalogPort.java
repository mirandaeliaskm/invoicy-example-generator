package br.com.migrate.invoicy.examples.domain.port;

import br.com.migrate.invoicy.examples.domain.model.FiscalModule;
import br.com.migrate.invoicy.examples.domain.model.UseCaseDefinition;

import java.util.List;

public interface UseCaseCatalogPort {
    List<UseCaseDefinition> listAll();
    List<UseCaseDefinition> listByModule(FiscalModule module);
    UseCaseDefinition getRequired(String useCaseId);
}
