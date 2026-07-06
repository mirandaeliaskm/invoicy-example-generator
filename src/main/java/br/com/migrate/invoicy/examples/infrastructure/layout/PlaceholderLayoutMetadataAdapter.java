package br.com.migrate.invoicy.examples.infrastructure.layout;

import br.com.migrate.invoicy.examples.domain.layout.FieldDefinition;
import br.com.migrate.invoicy.examples.domain.model.FiscalModule;
import br.com.migrate.invoicy.examples.domain.port.LayoutMetadataPort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Ponto de extensão para importar o layout oficial da Migrate, por exemplo o XLSX NF-e/NFC-e.
 *
 * A implementação inicial não tenta inferir o layout completo automaticamente. Isso evita publicar
 * validações incompletas como se fossem regras oficiais. A evolução recomendada é criar um
 * XlsxLayoutMetadataAdapter dedicado e validado pelo time BR + Qualidade.
 */
@Component
public class PlaceholderLayoutMetadataAdapter implements LayoutMetadataPort {

    @Override
    public List<FieldDefinition> listFields(FiscalModule module, String layoutVersion) {
        return List.of();
    }
}
