package br.com.joaotube.infra.exception;

public record DadosArgumentTypeMismath(String field, String rejectedValue, String message) {
}
