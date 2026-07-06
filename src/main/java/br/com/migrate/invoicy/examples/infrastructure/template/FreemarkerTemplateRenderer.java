package br.com.migrate.invoicy.examples.infrastructure.template;

import br.com.migrate.invoicy.examples.domain.port.TemplateRendererPort;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

@Component
public class FreemarkerTemplateRenderer implements TemplateRendererPort {

    private final Configuration configuration;

    public FreemarkerTemplateRenderer() {
        this.configuration = new Configuration(Configuration.VERSION_2_3_34);
        this.configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        this.configuration.setTemplateLoader(new ClassTemplateLoader(getClass().getClassLoader(), "/"));
        this.configuration.setLocalizedLookup(false);
            this.configuration.setLocale(Locale.US);
            this.configuration.setNumberFormat("computer");
    }

    @Override
    public String render(String templatePath, Map<String, Object> context) {
        try {
            Template template = configuration.getTemplate(templatePath);
            StringWriter writer = new StringWriter();
            template.process(context, writer);
            return writer.toString();
        } catch (IOException | TemplateException exception) {
            throw new IllegalStateException("Erro ao renderizar template: " + templatePath, exception);
        }
    }
}
