package br.com.joaotube.controller;


import br.com.joaotube.dto.CategoriaInputDto;
import br.com.joaotube.dto.CategoriaResponseDto;
import br.com.joaotube.dto.VideoResponseDto;
import br.com.joaotube.service.CategoriaService;
import br.com.joaotube.service.VideoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final VideoService videoService;

    public CategoriaController(CategoriaService categoriaService, VideoService videoService) {
        this.categoriaService = categoriaService;
        this.videoService = videoService;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDto> criar(@RequestBody @Valid CategoriaInputDto input, UriComponentsBuilder uriComponentsBuilder) {
        var categoria = categoriaService.criar(input);
        URI uri = uriComponentsBuilder.path("/categorias/{id}")
                .buildAndExpand(categoria.id()).toUri();
        return ResponseEntity.created(uri).body(categoria);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoriaResponseDto> exibirPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.exibirPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaResponseDto>> exibirTodos(@PageableDefault(sort = {"titulo"}, size = 5) Pageable pageable) {
        return ResponseEntity.ok(categoriaService.exibirTodos(pageable));
    }

    @GetMapping("/{categoriaId}/videos")
    public ResponseEntity<Page<VideoResponseDto>> exibirVideosPorCategoria(@PathVariable Long categoriaId, @PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(videoService.exibirPorIdCategoria(categoriaId, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> editar(@PathVariable Long id, @RequestBody CategoriaInputDto input) {
        return ResponseEntity.ok(categoriaService.editar(id, input));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id) {
        categoriaService.excluir(id);
        return ResponseEntity.ok("Categoria id= " + id + " excluído com sucesso");
    }
}

