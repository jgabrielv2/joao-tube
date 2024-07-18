package br.com.joaotube.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // <- indica ao springboot que essa é uma classe que testa um repository.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // <- indica ao springboot que
// é para utilizar o mesmo banco de dados da aplicação, sem substituir por um banco de dados em memória.
@ActiveProfiles("test") // <- indica ao springboot que é para carregar o application-"test".properties
class VideoRepositoryTest {

    @Test
    void findByTituloContainsIgnoreCase() {
    }

    @Test
    void findByCategoria_Id() {
    }
}