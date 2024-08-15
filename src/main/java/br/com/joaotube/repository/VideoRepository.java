package br.com.joaotube.repository;

import br.com.joaotube.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
    Page<Video> findByTituloContainsIgnoreCase(String titulo, Pageable pageable);
    Page<Video> findByCategoria_Id(Long id, Pageable pageable);

    

}