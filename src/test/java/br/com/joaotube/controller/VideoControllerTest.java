package br.com.joaotube.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc // <- anotacao para permitir que o MockMvc seja injetado.
class VideoControllerTest {

    @Autowired
    private MockMvc mvc; // <- simula requisições HTTP usando o padrão mvc

    @Test
    @DisplayName("Deveria retornar httpStatus 400 quando informacoes estão inválidas")
    void cadastrarVideoScenario1() throws Exception {
        var response = mvc.perform(MockMvcRequestBuilders.post("/videos"))
                .andReturn().getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}