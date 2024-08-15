package br.com.joaotube.repository;

import br.com.joaotube.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByTituloContainsIgnoreCase(String titulo);
    List<Video> findByCategoria_Id(Long id);
}