package br.com.joaotube.dto;

import java.io.Serializable;

/**
 * DTO for {@link br.com.joaotube.model.Video}
 */
public record VideoResponseDto(Long id, String titulo, String descricao, String url) implements Serializable {
}