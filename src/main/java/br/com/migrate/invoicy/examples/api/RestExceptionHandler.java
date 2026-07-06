package br.com.migrate.invoicy.examples.api;

import br.com.migrate.invoicy.examples.api.dto.ErrorResponse;
import br.com.migrate.invoicy.examples.domain.exception.FiscalValidationException;
import br.com.migrate.invoicy.examples.domain.exception.UseCaseNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UseCaseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUseCaseNotFound(UseCaseNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(OffsetDateTime.now(), exception.getMessage(), List.of()));
    }

    @ExceptionHandler(FiscalValidationException.class)
    public ResponseEntity<ErrorResponse> handleFiscalValidation(FiscalValidationException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponse(
                        OffsetDateTime.now(),
                        exception.getMessage(),
                        exception.validationResult().messages()
                ));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleValidation(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(OffsetDateTime.now(), exception.getMessage(), List.of()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(OffsetDateTime.now(), exception.getMessage(), List.of()));
    }
}
