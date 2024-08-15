package br.com.joaotube.repository;

import br.com.joaotube.model.Video;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest // <- indica ao springboot que essa é uma classe que testa um repository.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // <- indica ao springboot que
// é para utilizar o mesmo banco de dados da aplicação, sem substituir por um banco de dados em memória.
@ActiveProfiles("test") // <- indica ao springboot que é para carregar o application-"test".properties
class VideoRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VideoRepository videoRepository;

    @Test
    @DisplayName("Deveria encontrar um vídeo cadastrado buscando por trecho do título")
    void findByTituloContainsIgnoreCaseScenario1() {
        var video = cadastrarVideo("A Casa do Mickey Mouse da Disney",
                "Venha se juntar a esta incrível aventura",
                "www.example.com/mickey_mouse_clubhouse");

        List<Video> videoPesquisado = videoRepository.findByTituloContainsIgnoreCase("mickey");
        Assertions.assertThat(videoPesquisado.contains(video)).isTrue();

    }

    @Test
    @DisplayName("Lista de vídeos não deveria conter video que não tenha a palavra informada como pesquisa")
    void findByTituloContainsIgnoreCaseScenario2() {
        var video = cadastrarVideo("A Regra de L'Hopital",
                "Aprenda a resolver limites utilizando a regra de L'Hopital",
                "www.example.com/lhopital");

        List<Video> videoPesquisado = videoRepository.findByTituloContainsIgnoreCase("derivada");
        Assertions.assertThat(videoPesquisado.contains(video)).isFalse();

    }


    private Video cadastrarVideo(String titulo, String descricao, String url) {
        Video video = new Video();
        video.setTitulo(titulo);
        video.setDescricao(descricao);
        video.setUrl(url);
        return entityManager.persist(video);
    }
}