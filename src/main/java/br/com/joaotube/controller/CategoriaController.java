package br.com.joaotube.controller;


import br.com.joaotube.dto.CategoriaInputDto;
import br.com.joaotube.dto.CategoriaResponseDto;
import br.com.joaotube.infra.exception.CategoriaNotFoundException;
import br.com.joaotube.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
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
    public ResponseEntity<List<CategoriaResponseDto>> exibirTodos() {
        return ResponseEntity.ok(categoriaService.exibirTodos());
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

