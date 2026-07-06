package br.com.migrate.invoicy.examples.api;

import br.com.migrate.invoicy.examples.api.dto.ConfigureNextNoteNumberRequest;
import br.com.migrate.invoicy.examples.domain.model.SequenceStatus;
import br.com.migrate.invoicy.examples.domain.port.GenerationSequencePort;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sequence")
public class SequenceController {

    private final GenerationSequencePort generationSequencePort;

    public SequenceController(GenerationSequencePort generationSequencePort) {
        this.generationSequencePort = generationSequencePort;
    }

    @GetMapping("/status")
    public SequenceStatus status() {
        return generationSequencePort.status();
    }

    @PostMapping("/configure-next")
    public SequenceStatus configureNext(@Valid @RequestBody ConfigureNextNoteNumberRequest request) {
        return generationSequencePort.configureNextNoteNumber(request.nextNoteNumber());
    }
}
