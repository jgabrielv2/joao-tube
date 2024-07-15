package br.com.joaotube.service;

import br.com.joaotube.dto.VideoInputDto;
import br.com.joaotube.dto.VideoResponseDto;
import br.com.joaotube.infra.exception.CategoriaNotFoundException;
import br.com.joaotube.infra.exception.VideoNotFoundException;
import br.com.joaotube.model.Categoria;
import br.com.joaotube.model.Video;
import br.com.joaotube.repository.CategoriaRepository;
import br.com.joaotube.repository.VideoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final CategoriaRepository categoriaRepository;
    private static final Long ID_CATEGORIA_PADRAO = 1L;

    public VideoService(VideoRepository videoRepository, CategoriaRepository categoriaRepository) {
        this.videoRepository = videoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public VideoResponseDto criar(VideoInputDto input) {
        Categoria c = input.idCategoria() == null
                ? categoriaRepository.findById(ID_CATEGORIA_PADRAO)
                .orElseThrow(() -> new CategoriaNotFoundException("categoria LIVRE não encontrada"))
                : categoriaRepository.findById(input.idCategoria())
                .orElseThrow(() -> new CategoriaNotFoundException("categoria não encontrada"));
        Video v = new Video();
        v.setCategoria(c);
        v.setTitulo(input.titulo());
        v.setDescricao(input.descricao());
        v.setUrl(input.url());
        videoRepository.save(v);
        return new VideoResponseDto(v.getId(), v.getCategoria().getId(), v.getTitulo(), v.getDescricao(), v.getUrl());
    }

    public List<VideoResponseDto> exibirTodos() {
        return videoRepository.findAll().stream().map(
                v -> new VideoResponseDto(v.getId(), v.getCategoria().getId(),
                        v.getTitulo(), v.getDescricao(), v.getUrl())).toList();

    }

    public VideoResponseDto exibirPorId(Long id) {
        Video v = videoRepository.findById(id).orElseThrow(() -> new VideoNotFoundException("Não encontrado"));
        return new VideoResponseDto(v.getId(), v.getCategoria().getId(), v.getTitulo(), v.getDescricao(), v.getUrl());
    }

    public List<VideoResponseDto> exibirPorIdCategoria(Long idCategoria) {
        return videoRepository.findByCategoria_Id(idCategoria).stream().map(
                v -> new VideoResponseDto(v.getId(), v.getCategoria().getId(),
                        v.getTitulo(), v.getDescricao(), v.getUrl())).toList();
    }

    public List<VideoResponseDto> buscarPorTituloContendo(String titulo){
        return videoRepository.findByTituloContainsIgnoreCase(titulo).stream().map(
                v -> new VideoResponseDto(v.getId(), v.getCategoria().getId(),
                        v.getTitulo(), v.getDescricao(), v.getUrl())).toList();
    }

    @Transactional
    public VideoResponseDto editar(Long id, VideoInputDto input) {
        Video v = videoRepository.findById(id).orElseThrow(() -> new VideoNotFoundException("Não encontrado"));
        if (input.idCategoria() != null) {
            v.setCategoria(categoriaRepository.findById(input.idCategoria())
                    .orElseThrow(() -> new CategoriaNotFoundException("categoria não encontrada")));
        }
        if (input.titulo() != null) {
            v.setTitulo(input.titulo());
        }
        if (input.descricao() != null) {
            v.setDescricao(input.descricao());
        }
        if (input.url() != null) {
            v.setUrl(input.url());
        }
        videoRepository.save(v);
        return new VideoResponseDto(v.getId(), v.getCategoria().getId(), v.getTitulo(), v.getDescricao(), v.getUrl());
    }

    @Transactional
    public void excluir(Long id) {
        var video = videoRepository.findById(id).orElseThrow(() -> new VideoNotFoundException("Vídeo não encontrado com id " + id));
        videoRepository.delete(video);
    }

}
