package br.com.joaotube.dto;

import br.com.joaotube.model.Categoria;

import java.io.Serializable;

/**
 * DTO for {@link Categoria}
 */
public record CategoriaResponseDto(Long id, String titulo, String cor) implements Serializable {
}