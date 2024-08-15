package br.com.joaotube.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link br.com.joaotube.model.Video}
 */
public record VideoInputDto(@NotBlank String titulo,
                            Long idCategoria,
                            @NotBlank String descricao,
                            @NotBlank String url) implements Serializable {
}