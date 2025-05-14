package com.guiweber.estacionamento.web.controller;

import com.guiweber.estacionamento.entities.Vaga;
import com.guiweber.estacionamento.service.VagaService;
import com.guiweber.estacionamento.web.dto.VagaCreateDto;
import com.guiweber.estacionamento.web.dto.VagaResponseDto;
import com.guiweber.estacionamento.web.dto.mapper.VagaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/vagas")
@RequiredArgsConstructor
public class VagaController {

    private final VagaService vagaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid VagaCreateDto vagaCreateDto) {
        Vaga vaga = VagaMapper.toVaga(vagaCreateDto);
        vagaService.save(vaga);
        URI location =  ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{codigo}").buildAndExpand(vaga.getCodigo()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDto> getByCod(@PathVariable String codigo) {
        Vaga vaga = vagaService.findByCod(codigo);
        return ResponseEntity.ok(VagaMapper.toDto(vaga));
    }
}
