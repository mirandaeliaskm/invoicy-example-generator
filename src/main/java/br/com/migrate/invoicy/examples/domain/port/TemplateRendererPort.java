package br.com.migrate.invoicy.examples.domain.port;

import java.util.Map;

public interface TemplateRendererPort {
    String render(String templatePath, Map<String, Object> context);
}
