package com.guiweber.estacionamento.web.controller;

import com.guiweber.estacionamento.entities.ClienteVaga;
import com.guiweber.estacionamento.service.EstacionamentoService;
import com.guiweber.estacionamento.web.dto.EstacionamentoCreateDto;
import com.guiweber.estacionamento.web.dto.EstacionamentoResponseDto;
import com.guiweber.estacionamento.web.dto.mapper.ClienteVagaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/estacionamentos")
public class EstacionamentoController {

    private final EstacionamentoService estacionamentoService;


    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDto> checkin(@RequestBody @Valid EstacionamentoCreateDto estacionamentoCreateDto) {
        ClienteVaga clienteVaga = ClienteVagaMapper.toClienteVaga(estacionamentoCreateDto);
        estacionamentoService.checkIn(clienteVaga);

        EstacionamentoResponseDto estacionamentoResponseDto = ClienteVagaMapper.toDto(clienteVaga);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{recibo}").buildAndExpand(clienteVaga.getRecibo()).toUri();
        return ResponseEntity.created(location).body(estacionamentoResponseDto);
    }
}
