package com.guiweber.estacionamento.repository.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ClienteVagaProjection {
    String getPlaca();

    String getClienteCpf();

    String getRecibo();

    String getVagaCodigo();

    String getMarca();

    String getModelo();

    String getCor();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getDataEntrada();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getDataSaida();

    BigDecimal getValor();
    BigDecimal getDesconto();
}
