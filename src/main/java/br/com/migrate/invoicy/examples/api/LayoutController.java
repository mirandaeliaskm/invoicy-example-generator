package br.com.migrate.invoicy.examples.api;

import br.com.migrate.invoicy.examples.domain.layout.FieldDefinition;
import br.com.migrate.invoicy.examples.domain.model.FiscalModule;
import br.com.migrate.invoicy.examples.domain.port.LayoutMetadataPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/layouts")
public class LayoutController {

    private final LayoutMetadataPort layoutMetadataPort;

    public LayoutController(LayoutMetadataPort layoutMetadataPort) {
        this.layoutMetadataPort = layoutMetadataPort;
    }

    @GetMapping("/{module}/fields")
    public List<FieldDefinition> listFields(
            @PathVariable FiscalModule module,
            @RequestParam(defaultValue = "4.00") String version
    ) {
        return layoutMetadataPort.listFields(module, version);
    }
}
