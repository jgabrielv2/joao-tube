package br.com.joaotube.controller;

import br.com.joaotube.dto.VideoInputDto;
import br.com.joaotube.dto.VideoResponseDto;
import br.com.joaotube.service.VideoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc // <- anotacao para permitir que o MockMvc seja injetado.
@AutoConfigureJsonTesters // <- anotação para permitir que o JacksonTester seja injetado.
class VideoControllerTest {

    @Autowired
    private MockMvc mvc; // <- simula requisições HTTP usando o padrão mvc

    @MockBean
    private VideoService videoService;

    @Autowired
    private JacksonTester<VideoResponseDto> videoResponseDtoJson;

    @Autowired
    private JacksonTester<VideoInputDto> videoInputDtoJson;


    @Test
    @DisplayName("Deveria retornar httpStatus 400 quando informacoes estão inválidas")
    void cadastrarVideoScenario1() throws Exception {
        var response = mvc.perform(post("/videos"))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria retornar httpStatus 201 quando informacoes estão válidas")
    void cadastrarVideoScenario2() throws Exception {

        var videoInputDto = new VideoInputDto(
                "Mickey Mouse Funhouse EP 01", null, "ep 01", "www.example.com");

        VideoResponseDto responseDto = new VideoResponseDto(
                null, null, videoInputDto.titulo(), videoInputDto.descricao(), videoInputDto.url()
        );
        when(videoService.criar(any())).thenReturn(responseDto);
        MockHttpServletResponse response = mvc.perform(post("/videos")
                        .contentType(APPLICATION_JSON)
                        .content(videoInputDtoJson.write(videoInputDto).getJson())
                )
                .andReturn().getResponse();


        var json = videoResponseDtoJson.write(responseDto).getJson();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(json);
    }

    @Test
    @DisplayName("Deveria retornar httpStatus 200 se video for achado pelo id informado")
    void buscarVideoScenario1() throws Exception {

        var videoResponse = new VideoResponseDto(
                1L, 1L, "Título", "Descricao", "url");
        when(videoService.exibirPorId(1L)).thenReturn(videoResponse);

        MockHttpServletResponse response = mvc.perform(get("/videos/1")
                        .accept(APPLICATION_JSON)
                        .characterEncoding(UTF_8))
                .andReturn().getResponse();
        var json = videoResponseDtoJson.write(videoResponse).getJson();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString(UTF_8)).isEqualTo(json);

    }
}