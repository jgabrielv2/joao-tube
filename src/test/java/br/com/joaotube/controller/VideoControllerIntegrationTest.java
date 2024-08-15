package br.com.joaotube.controller;

import br.com.joaotube.dto.VideoInputDto;
import br.com.joaotube.dto.VideoResponseDto;
import br.com.joaotube.repository.VideoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
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
@Sql(scripts = "/setup_categoria_livre.sql")
public class VideoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private JacksonTester<VideoResponseDto> videoResponseDtoJson;

    @Autowired
    private JacksonTester<VideoInputDto> videoInputDtoJson;

    @Test
    @DisplayName("Deveria criar categoria e persistir em banco de dados")
    void scenario1() throws Exception {

        videoRepository.deleteAll();

        var input = new VideoInputDto("titulo", null, "descricao", "url");
        var jsonRequest = videoInputDtoJson.write(input).getJson();
        var request = mockMvc.perform(post("/videos")
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn().getResponse();

        assertThat(request.getStatus()).isEqualTo(CREATED.value());

        var videos = videoRepository.findAll();

        assertThat(videos).hasSize(1);
        assertThat(videos.getFirst().getTitulo()).isEqualTo(input.titulo());
        assertThat(videos.getFirst().getDescricao()).isEqualTo(input.descricao());
        assertThat(videos.getFirst().getUrl()).isEqualTo(input.url());
    }

}
