package br.com.migrate.invoicy.examples.api;

import br.com.migrate.invoicy.examples.application.ListUseCasesService;
import br.com.migrate.invoicy.examples.domain.model.FiscalModule;
import br.com.migrate.invoicy.examples.domain.model.UseCaseDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/use-cases")
public class UseCaseController {

    private final ListUseCasesService listUseCasesService;

    public UseCaseController(ListUseCasesService listUseCasesService) {
        this.listUseCasesService = listUseCasesService;
    }

    @GetMapping
    public List<UseCaseDefinition> list(@RequestParam(required = false) FiscalModule module) {
        return listUseCasesService.execute(module);
    }

    @GetMapping("/{id}")
    public UseCaseDefinition getById(@PathVariable String id) {
        return listUseCasesService.getRequired(id);
    }
}
