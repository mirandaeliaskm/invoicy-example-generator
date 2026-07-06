package br.com.migrate.invoicy.examples.domain.port;

import br.com.migrate.invoicy.examples.domain.model.GeneratedExample;

public interface ExamplePackagePort {
    byte[] packageAsZip(GeneratedExample example);
}
