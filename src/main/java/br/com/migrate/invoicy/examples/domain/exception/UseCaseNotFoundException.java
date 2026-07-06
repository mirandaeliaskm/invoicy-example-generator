package br.com.migrate.invoicy.examples.domain.exception;

public class UseCaseNotFoundException extends RuntimeException {
    public UseCaseNotFoundException(String useCaseId) {
        super("Use case fiscal não encontrado: " + useCaseId);
    }
}
