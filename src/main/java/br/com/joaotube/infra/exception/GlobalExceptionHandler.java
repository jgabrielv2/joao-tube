package br.com.joaotube.infra.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(VideoNotFoundException.class)
    public ResponseEntity<String> tratarErroVideoNaoEncontrado(VideoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> tratarViolacaoRestricaoCampo(ConstraintViolationException ex) {
        List<DadosErroValidacao> erros = ex.getConstraintViolations().stream().map(violation -> new DadosErroValidacao(
                violation.getPropertyPath().toString(),
                violation.getMessage())).toList();
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException ex) {
        List<FieldError> erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }


}
