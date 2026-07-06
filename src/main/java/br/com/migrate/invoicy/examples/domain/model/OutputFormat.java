package br.com.migrate.invoicy.examples.domain.model;

public enum OutputFormat {
    XML("xml"),
    JSON("json"),
    README("md");

    private final String extension;

    OutputFormat(String extension) {
        this.extension = extension;
    }

    public String extension() {
        return extension;
    }
}
