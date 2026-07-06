package br.com.migrate.invoicy.examples.api;

import br.com.migrate.invoicy.examples.api.dto.GenerateExampleRequest;
import br.com.migrate.invoicy.examples.api.dto.GeneratedExampleResponse;
import br.com.migrate.invoicy.examples.application.GenerateExampleService;
import br.com.migrate.invoicy.examples.domain.model.GenerateExampleCommand;
import br.com.migrate.invoicy.examples.domain.model.GeneratedExample;
import br.com.migrate.invoicy.examples.domain.port.ExamplePackagePort;
import jakarta.validation.Valid;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/examples")
public class ExampleGeneratorController {

    private final GenerateExampleService generateExampleService;
    private final ExamplePackagePort examplePackagePort;

    public ExampleGeneratorController(GenerateExampleService generateExampleService, ExamplePackagePort examplePackagePort) {
        this.generateExampleService = generateExampleService;
        this.examplePackagePort = examplePackagePort;
    }

    @PostMapping("/generate")
    public GeneratedExampleResponse generate(@Valid @RequestBody GenerateExampleRequest request) {
        GeneratedExample generatedExample = generateExampleService.generate(toCommand(request));
        return GeneratedExampleResponse.from(generatedExample);
    }

    @PostMapping("/generate/package")
    public ResponseEntity<byte[]> generatePackage(@Valid @RequestBody GenerateExampleRequest request) {
        GeneratedExample generatedExample = generateExampleService.generate(toCommand(request));
        byte[] zip = examplePackagePort.packageAsZip(generatedExample);

        String fileName = generatedExample.useCase().id().toLowerCase() + "-" + generatedExample.generationId() + ".zip";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(fileName).build().toString())
                .body(zip);
    }

    private GenerateExampleCommand toCommand(GenerateExampleRequest request) {
        return new GenerateExampleCommand(request.useCaseId(), request.outputFormats(), request.overrides());
    }
}
