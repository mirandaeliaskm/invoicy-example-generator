package br.com.migrate.invoicy.examples.infrastructure.layout;

import br.com.migrate.invoicy.examples.domain.layout.FieldDefinition;
import br.com.migrate.invoicy.examples.domain.layout.FieldRequirement;
import br.com.migrate.invoicy.examples.domain.model.FiscalModule;
import br.com.migrate.invoicy.examples.domain.port.LayoutMetadataPort;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Adapter de metadados do Layout de Envio InvoiCy.
 *
 * A primeira versão do projeto gerava uma estrutura próxima ao XML oficial autorizado pela SEFAZ.
 * Esta implementação passa a ler a extração versionada da planilha "Layout 4.0 NFe_NFCe.xlsx",
 * mantendo a hierarquia que a aplicação InvoiCy espera receber: Envio, ModeloDocumento, Versao,
 * ide, emit, dest, det/detItem, total/ICMStot etc.
 */
@Component
public class PlaceholderLayoutMetadataAdapter implements LayoutMetadataPort {

    private static final String NFE_LAYOUT_RESOURCE = "layouts/nfe/layout-envio-invoicy-4.00.yml";

    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory()).findAndRegisterModules();

    @Override
    public List<FieldDefinition> listFields(FiscalModule module, String layoutVersion) {
        if (module != FiscalModule.NFE) {
            return List.of();
        }

        Map<String, Object> layout = loadLayout();
        String availableVersion = stringValue(layout.get("layoutVersion"));
        if (layoutVersion != null && !layoutVersion.isBlank() && !availableVersion.equals(layoutVersion)) {
            return List.of();
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> fields = (List<Map<String, Object>>) layout.getOrDefault("fields", List.of());

        return fields.stream()
                .map(this::toFieldDefinition)
                .toList();
    }

    private Map<String, Object> loadLayout() {
        try (InputStream inputStream = new ClassPathResource(NFE_LAYOUT_RESOURCE).getInputStream()) {
            return yamlMapper.readValue(inputStream, new TypeReference<>() {});
        } catch (IOException exception) {
            throw new IllegalStateException("Não foi possível carregar metadados do Layout de Envio InvoiCy: " + NFE_LAYOUT_RESOURCE, exception);
        }
    }

    private FieldDefinition toFieldDefinition(Map<String, Object> field) {
        return new FieldDefinition(
                stringValue(field.get("path")),
                stringValue(field.get("tag")),
                stringValue(field.get("description")),
                stringValue(field.get("type")),
                null,
                null,
                toRequirement(stringValue(field.get("required"))),
                "Ocorrência: " + stringValue(field.get("occurrence")) + "; ID: " + stringValue(field.get("id")) + "; Pai: " + stringValue(field.get("parent")),
                stringValue(field.get("version"))
        );
    }

    private FieldRequirement toRequirement(String required) {
        return switch (required) {
            case "S" -> FieldRequirement.REQUIRED;
            case "N" -> FieldRequirement.OPTIONAL;
            default -> FieldRequirement.CONDITIONAL;
        };
    }

    private String stringValue(Object value) {
        return value == null ? "" : value.toString();
    }
}
