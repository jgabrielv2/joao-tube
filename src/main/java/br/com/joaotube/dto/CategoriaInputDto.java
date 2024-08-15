package br.com.joaotube.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link br.com.joaotube.model.Categoria}
 */
public record CategoriaInputDto(@NotBlank(message = "título não pode estar em branco") String titulo,
                                @NotBlank(message = "cor não pode estar em branco") String cor) implements Serializable {
}