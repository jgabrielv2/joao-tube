package br.com.joaotube.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest // <- indica ao springboot que essa é uma classe que testa um repository.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // <- indica ao springboot que
// é para utilizar o mesmo banco de dados da aplicação, sem substituir por um banco de dados em memória.
@ActiveProfiles("test") // <- indica ao springboot que é para carregar o application-"test".properties
public class CategoriaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoriaRepository categoriaRepository;


}
