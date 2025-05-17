package com.guiweber.estacionamento;


import com.guiweber.estacionamento.web.dto.EstacionamentoCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/estacionamentos/estacionamentos-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/estacionamentos/estacionamentos-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class EstacionamentoIT {

    @Autowired
    WebTestClient webClient;

    @Test
    public void criarCheckin_comDadosValidos_retornarStatus201() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("ABC-1234")
                .marca("FIAT")
                .modelo("PALIO 1.0")
                .cor("AZUL")
                .clienteCpf("09191773016")
                .build();

        webClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webClient, "ana@email.com.br", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION)
                .expectBody()
                .jsonPath("placa").isEqualTo("ABC-1234")
                .jsonPath("marca").isEqualTo("FIAT")
                .jsonPath("modelo").isEqualTo("PALIO 1.0")
                .jsonPath("cor").isEqualTo("AZUL")
                .jsonPath("clienteCpf").isEqualTo("09191773016")
                .jsonPath("recibo").exists()
                .jsonPath("dataEntrada").exists()
                .jsonPath("vagaCodigo").exists();
    }

    @Test
    public void criarCheckin_comRoleCliente_retornarStatus403() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("ABC-1234")
                .marca("FIAT")
                .modelo("PALIO 1.0")
                .cor("AZUL")
                .clienteCpf("09191773016")
                .build();

        webClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webClient, "bob@email.com.br", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in");
    }

    @Test
    public void criarCheckin_comDadosInvalidos_retornarStatus422() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("")
                .marca("")
                .modelo("")
                .cor("")
                .clienteCpf("")
                .build();

        webClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webClient, "ana@email.com.br", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in");
    }

    @Test
    public void criarCheckin_comCpfInexistente_retornarStatus404() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("ABC-1234")
                .marca("FIAT")
                .modelo("PALIO 1.0")
                .cor("AZUL")
                .clienteCpf("16320046074")
                .build();

        webClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webClient, "ana@email.com.br", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in");
    }

    @Sql(scripts = "/sql/estacionamentos/estacionamentos-insert-vagas-ocupadas.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/estacionamentos/estacionamentos-delete-vagas-ocupadas.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void criarCheckin_comVagasOcupadas_retornarStatus404() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("ABC-1234")
                .marca("FIAT")
                .modelo("PALIO 1.0")
                .cor("AZUL")
                .clienteCpf("09191773016")
                .build();

        webClient.post().uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webClient, "ana@email.com.br", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in");
    }


    @Test
    public void getCheckin_comAdmin_retornarStatus200() {
        webClient.get().uri("/api/v1/estacionamentos/check-in/{recibo}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(webClient, "ana@email.com.br", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("placa").isEqualTo("FIT-1020")
                .jsonPath("marca").isEqualTo("FIAT")
                .jsonPath("modelo").isEqualTo("PALIO")
                .jsonPath("cor").isEqualTo("VERDE")
                .jsonPath("clienteCpf").isEqualTo("98401203015")
                .jsonPath("recibo").isEqualTo("20230313-101300")
                .jsonPath("dataEntrada").isEqualTo("2023-03-13 10:15:00")
                .jsonPath("vagaCodigo").isEqualTo("A-01");
    }

    @Test
    public void getCheckin_comCliente_retornarStatus200() {
        webClient.get().uri("/api/v1/estacionamentos/check-in/{recibo}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(webClient, "bob@email.com.br", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("placa").isEqualTo("FIT-1020")
                .jsonPath("marca").isEqualTo("FIAT")
                .jsonPath("modelo").isEqualTo("PALIO")
                .jsonPath("cor").isEqualTo("VERDE")
                .jsonPath("clienteCpf").isEqualTo("98401203015")
                .jsonPath("recibo").isEqualTo("20230313-101300")
                .jsonPath("dataEntrada").isEqualTo("2023-03-13 10:15:00")
                .jsonPath("vagaCodigo").isEqualTo("A-01");
    }

    @Test
    public void getCheckin_comReciboInexistente_retornarStatus404() {
        webClient.get().uri("/api/v1/estacionamentos/check-in/{recibo}", "20230313-101303")
                .headers(JwtAuthentication.getHeaderAuthorization(webClient, "bob@email.com.br", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in/20230313-101303");
    }

}
