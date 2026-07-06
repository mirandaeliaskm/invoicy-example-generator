package br.com.migrate.invoicy.examples.infrastructure.packageing;

import br.com.migrate.invoicy.examples.domain.model.GeneratedExample;
import br.com.migrate.invoicy.examples.domain.model.OutputFormat;
import br.com.migrate.invoicy.examples.domain.port.ExamplePackagePort;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ZipExamplePackage implements ExamplePackagePort {

    @Override
    public byte[] packageAsZip(GeneratedExample example) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream, StandardCharsets.UTF_8)) {

            for (Map.Entry<OutputFormat, String> entry : example.files().entrySet()) {
                String fileName = fileName(example, entry.getKey());
                zipOutputStream.putNextEntry(new ZipEntry(fileName));
                zipOutputStream.write(entry.getValue().getBytes(StandardCharsets.UTF_8));
                zipOutputStream.closeEntry();
            }

            zipOutputStream.finish();
            return outputStream.toByteArray();
        } catch (IOException exception) {
            throw new IllegalStateException("Erro ao gerar pacote zip do exemplo.", exception);
        }
    }

    private String fileName(GeneratedExample example, OutputFormat outputFormat) {
        return example.useCase().id().toLowerCase() + "/" + example.useCase().id().toLowerCase() + "." + outputFormat.extension();
    }
}
