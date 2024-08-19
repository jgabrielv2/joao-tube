package br.com.joaotube.infra.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(VideoNotFoundException.class)
    public ResponseEntity<String> handleVideoNotFound(VideoNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CategoriaNotFoundException.class)
    public ResponseEntity<String> handleCategoriaNotFound(CategoriaNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<DadosArgumentTypeMismath> handleArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        String field = e.getName();
        String rejectedValue = String.valueOf(e.getValue()); // Safe conversion to String
        String message = String.format("O valor '%s' não é válido para o campo '%s'", rejectedValue, field);
        DadosArgumentTypeMismath error = new DadosArgumentTypeMismath(field, rejectedValue, message);
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleContraintViolation(ConstraintViolationException e) {
        List<DadosErroValidacao> erros = e.getConstraintViolations().stream().map(violation -> new DadosErroValidacao(
                violation.getPropertyPath().toString(),
                violation.getMessage())).toList();
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<FieldError> erros = e.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<Void> handlePropertyReference(PropertyReferenceException e){
        return ResponseEntity.status(FOUND)
                .header("Location", "/videos")
                .build();
    }


}
