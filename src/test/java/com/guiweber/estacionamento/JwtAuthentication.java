package com.guiweber.estacionamento;

import com.guiweber.estacionamento.jwt.JwtToken;
import com.guiweber.estacionamento.web.dto.UserLoginDto;
import org.springframework.test.web.reactive.server.WebTestClient;

import org.springframework.http.HttpHeaders;

import java.util.function.Consumer;

public class JwtAuthentication {

    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient webTestClient, String username, String password) {
        String token = webTestClient.post().uri("api/v1/auth").bodyValue(new UserLoginDto(username, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class).returnResult().getResponseBody().getToken();

        return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

}
