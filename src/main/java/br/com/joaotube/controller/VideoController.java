package br.com.joaotube.controller;

import br.com.joaotube.dto.VideoInputDto;
import br.com.joaotube.dto.VideoResponseDto;
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
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    public ResponseEntity<VideoResponseDto> criar(@RequestBody @Valid VideoInputDto input, UriComponentsBuilder uriComponentsBuilder) {
        var video = videoService.criar(input);
        URI uri = uriComponentsBuilder.path("/videos/{id}")
                .buildAndExpand(video.id()).toUri();
        return ResponseEntity.created(uri).body(video);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponseDto> exibirPorId(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.exibirPorId(id));
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<Page<VideoResponseDto>> buscarPorIdCategoria(@PathVariable Long idCategoria, Pageable pageable){
        return ResponseEntity.ok(videoService.exibirPorIdCategoria(idCategoria, pageable));
    }

    @GetMapping("/")
    public ResponseEntity<Page<VideoResponseDto>> buscarPorTituloContendo(
            @RequestParam String search, @PageableDefault(size = 5) Pageable pageable) {
            return ResponseEntity.ok(videoService.buscarPorTituloContendo(search, pageable));
    }

    @GetMapping
    public ResponseEntity<Page<VideoResponseDto>> exibirTodos(@PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(videoService.exibirTodos(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoResponseDto> editar(@PathVariable Long id, @RequestBody VideoInputDto input) {
        return ResponseEntity.ok(videoService.editar(id, input));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id) {
        videoService.excluir(id);
        return ResponseEntity.ok("Vídeo id " + id + " excluído com sucesso");
    }
}
