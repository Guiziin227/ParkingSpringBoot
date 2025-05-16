package com.guiweber.estacionamento.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstacionamentoCreateDto {

    @NotBlank
    @Size(min = 8, max = 8, message = "A placa deve ter 8 caracteres")
    @Pattern(regexp = "[A-Z]{3}-[0-9]{4}", message = "A placa deve seguir o padrão AAA-0000")
    private String placa;
    @NotBlank
    private String marca;
    @NotBlank
    private String modelo;
    @NotBlank
    private String cor;

    @NotBlank
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 caracteres")
    @CPF
    private String clienteCpf;
}
