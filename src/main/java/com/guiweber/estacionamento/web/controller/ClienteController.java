package com.guiweber.estacionamento.web.controller;

import com.guiweber.estacionamento.entities.Cliente;
import com.guiweber.estacionamento.jwt.JwtUserDetails;
import com.guiweber.estacionamento.service.ClienteService;
import com.guiweber.estacionamento.service.UserService;
import com.guiweber.estacionamento.web.dto.ClienteCreateDto;
import com.guiweber.estacionamento.web.dto.ClienteResponseDto;
import com.guiweber.estacionamento.web.dto.mapper.ClienteMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> create(@RequestBody @Valid ClienteCreateDto dto,
                                                     @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        Cliente cliente = ClienteMapper.toCliente(dto);
        cliente.setUsuario(userService.findById(jwtUserDetails.getId()));
        clienteService.salvar(cliente);
        return ResponseEntity.status(201).body(ClienteMapper.toDto(cliente));
    }
}
