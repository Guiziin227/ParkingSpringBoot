package com.guiweber.estacionamento.web.controller;

import com.guiweber.estacionamento.entities.Vaga;
import com.guiweber.estacionamento.service.VagaService;
import com.guiweber.estacionamento.web.dto.VagaCreateDto;
import com.guiweber.estacionamento.web.dto.VagaResponseDto;
import com.guiweber.estacionamento.web.dto.mapper.VagaMapper;
import com.guiweber.estacionamento.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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

    @Operation(summary = "Create a new parking space",
            description = "Create a new parking space with the given details",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Parking space created successfully",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URI of the created parking space")),
                    @ApiResponse(responseCode = "409", description = "Parking space already exists",
                    content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid request data",
                    content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid VagaCreateDto vagaCreateDto) {
        Vaga vaga = VagaMapper.toVaga(vagaCreateDto);
        vagaService.save(vaga);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{codigo}").buildAndExpand(vaga.getCodigo()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Get parking space by code",
            description = "Retrieve a parking space by its unique code",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Parking space found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VagaResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Parking space not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{codigo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDto> getByCod(@PathVariable String codigo) {
        Vaga vaga = vagaService.findByCod(codigo);
        return ResponseEntity.ok(VagaMapper.toDto(vaga));
    }
}
