package br.com.migrate.invoicy.examples.domain.port;

import br.com.migrate.invoicy.examples.domain.layout.FieldDefinition;
import br.com.migrate.invoicy.examples.domain.model.FiscalModule;

import java.util.List;

public interface LayoutMetadataPort {
    List<FieldDefinition> listFields(FiscalModule module, String layoutVersion);
}
