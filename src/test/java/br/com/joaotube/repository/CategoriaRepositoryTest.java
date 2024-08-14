package br.com.joaotube.repository;

import br.com.joaotube.model.Categoria;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest // <- indica ao springboot que essa é uma classe que testa um repository.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // <- indica ao springboot que
// é para utilizar o mesmo banco de dados da aplicação, sem substituir por um banco de dados em memória.
@ActiveProfiles("test") // <- indica ao springboot que é para carregar o application-"test".properties
public class CategoriaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    @DisplayName("Deveria encontrar um video cadastrado ao buscar todos")
    void videoRepositoryScenario1(){
        var categoria = cadastrarCategoria("videoaulas", "verde");

        var categorias = categoriaRepository.findAll();
        assertThat(categorias.contains(categoria)).isTrue();
    }


    private Categoria cadastrarCategoria(String titulo, String cor) {
        var categoria = new Categoria();
        categoria.setTitulo(titulo);
        categoria.setCor(cor);
        return entityManager.persist(categoria);
    }
}
