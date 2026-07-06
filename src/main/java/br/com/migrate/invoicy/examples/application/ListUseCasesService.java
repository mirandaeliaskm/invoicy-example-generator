package br.com.migrate.invoicy.examples.application;

import br.com.migrate.invoicy.examples.domain.model.FiscalModule;
import br.com.migrate.invoicy.examples.domain.model.UseCaseDefinition;
import br.com.migrate.invoicy.examples.domain.port.UseCaseCatalogPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListUseCasesService {

    private final UseCaseCatalogPort useCaseCatalogPort;

    public ListUseCasesService(UseCaseCatalogPort useCaseCatalogPort) {
        this.useCaseCatalogPort = useCaseCatalogPort;
    }

    public List<UseCaseDefinition> execute(FiscalModule module) {
        if (module == null) {
            return useCaseCatalogPort.listAll();
        }
        return useCaseCatalogPort.listByModule(module);
    }

    public UseCaseDefinition getRequired(String id) {
        return useCaseCatalogPort.getRequired(id);
    }
}
