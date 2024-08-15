package br.com.joaotube.controller;

import br.com.joaotube.dto.CategoriaInputDto;
import br.com.joaotube.dto.CategoriaResponseDto;
import br.com.joaotube.service.CategoriaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc // <- anotacao para permitir que o MockMvc seja injetado.
@AutoConfigureJsonTesters // <- anotação para permitir que o JacksonTester seja injetado.
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @Autowired
    private JacksonTester<CategoriaResponseDto> categoriaResponseDtoJson;

    @Autowired
    private JacksonTester<CategoriaInputDto> categoriaInputDtoJson;

    @Test
    @DisplayName("Deveria retornar httpStatus 400 quando informacoes estao invalidas")
    void cadastrarCategoriaScenario1() throws Exception {
        var response = mockMvc.perform(post("/categorias"))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria retornar httpStatus 201 quando informacoes estão válidas")
    void cadastrarCategoriaScenario2() throws Exception {
        var categoriaInputDto = new CategoriaInputDto("videojogos", "amarelo");
        var categoriaResponseDto = new CategoriaResponseDto(null, categoriaInputDto.titulo(), categoriaInputDto.cor());

        when(categoriaService.criar(any())).thenReturn(categoriaResponseDto);

        var response = mockMvc.perform(post("/categorias")
                .contentType(APPLICATION_JSON)
                .content(categoriaInputDtoJson.write(categoriaInputDto).getJson()))
                .andReturn().getResponse();

        var json = categoriaResponseDtoJson.write(categoriaResponseDto).getJson();

        assertThat(response.getStatus()).isEqualTo(CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(json);

    }
    
    @Test
    @DisplayName("Deveria retornar httpStatus 200 se categoria for achado pelo id informado")
    void cadastrarCategoriaScenario3() throws Exception{

        var categoriaResponse = new CategoriaResponseDto(2L, "variedades", "verde");
        when(categoriaService.exibirPorId(2L)).thenReturn(categoriaResponse);

        var response = mockMvc.perform(get("/categorias/2")
                .accept(APPLICATION_JSON))
                .andReturn().getResponse();

        var json = categoriaResponseDtoJson.write(categoriaResponse).getJson();

        assertThat(response.getStatus()).isEqualTo(OK.value());
        assertThat(response.getContentAsString()).isEqualTo(json);
    }

}
