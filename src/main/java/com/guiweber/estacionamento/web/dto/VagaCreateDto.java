package com.guiweber.estacionamento.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VagaCreateDto {

    @NotBlank
    @Size(min = 4, max = 4, message = "O código deve ter 4 caracteres")
    private String codigo;

    @NotBlank
    @Pattern(regexp = "LIVRE|OCUPADA", message = "O status deve ser LIVRE ou OCUPADA")
    private String status;
}
