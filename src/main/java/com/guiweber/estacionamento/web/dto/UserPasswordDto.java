package com.guiweber.estacionamento.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPasswordDto {

    @NotBlank
    @Size(min = 6, max = 6, message = "A senha deve ter 6 caracteres")
    private String password;
    @NotBlank
    @Size(min = 6, max = 6, message = "A senha deve ter 6 caracteres")
    private String newPassword;
    @NotBlank
    @Size(min = 6, max = 6, message = "A senha deve ter 6 caracteres")
    private String confirmPassword;
}
