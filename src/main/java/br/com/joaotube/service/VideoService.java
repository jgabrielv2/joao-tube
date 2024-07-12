package br.com.joaotube.service;

import br.com.joaotube.dto.VideoInputDto;
import br.com.joaotube.dto.VideoResponseDto;
import br.com.joaotube.infra.exception.VideoNotFoundException;
import br.com.joaotube.model.Video;
import br.com.joaotube.repository.VideoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Transactional
    public VideoResponseDto criar(VideoInputDto input){
        Video v = new Video();
        v.setTitulo(input.titulo());
        v.setDescricao(input.descricao());
        v.setUrl(input.url());
        videoRepository.save(v);
        return new VideoResponseDto(v.getId(), v.getTitulo(), v.getDescricao(), v.getUrl());
    }

    public List<VideoResponseDto> exibirTodos() {
        return videoRepository.findAll().stream().map(
                v -> new VideoResponseDto(v.getId(), v.getTitulo(), v.getDescricao(), v.getUrl())).toList();

    }

    public VideoResponseDto exibirPorId(Long id){
        Video v = videoRepository.findById(id).orElseThrow(() -> new VideoNotFoundException("Não encontrado"));
        return new VideoResponseDto(v.getId(), v.getTitulo(), v.getDescricao(), v.getUrl());
    }

    @Transactional
    public VideoResponseDto editar(Long id, VideoInputDto input){
        Video v = videoRepository.findById(id).orElseThrow(() -> new VideoNotFoundException("Não encontrado"));
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
        return new VideoResponseDto(v.getId(), v.getTitulo(), v.getDescricao(), v.getUrl());
    }

    @Transactional
    public void excluir(Long id) {
        var video = videoRepository.findById(id).orElseThrow(() -> new VideoNotFoundException("Vídeo não encontrado com id " + id));
        videoRepository.delete(video);
    }

}
