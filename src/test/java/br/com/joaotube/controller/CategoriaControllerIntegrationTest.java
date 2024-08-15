package br.com.joaotube.controller;

import br.com.joaotube.dto.CategoriaInputDto;
import br.com.joaotube.dto.CategoriaResponseDto;
import br.com.joaotube.repository.CategoriaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CategoriaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private JacksonTester<CategoriaResponseDto> categoriaResponseDtoJson;

    @Autowired
    private JacksonTester<CategoriaInputDto> categoriaInputDtoJson;

    @Test
    @DisplayName("Deveria criar categoria e persistir em banco de dados")
    void scenario1() throws Exception {

        // limpa o banco de dados antes do teste
        categoriaRepository.deleteAll();

        // cria um dto de entrada
        var input = new CategoriaInputDto("videocomidas", "vermelho");

        // serializa o DTO para JSON
        var jsonRequest = categoriaInputDtoJson.write(input).getJson();

        //executa a requisicao POST
        var requisicao = mockMvc.perform(post("/categorias")
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn().getResponse();

        // verifica se o httpStatus da requisição foi realmente 201
        assertThat(requisicao.getStatus()).isEqualTo(CREATED.value());

        var categorias = categoriaRepository.findAll();

        assertThat(categorias).hasSize(1);
        assertThat(categorias.getFirst().getTitulo()).isEqualTo(input.titulo());
        assertThat(categorias.getFirst().getCor()).isEqualTo(input.cor());
    }



}
