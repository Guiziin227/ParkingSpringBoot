package com.guiweber.estacionamento.web.controller;

import com.guiweber.estacionamento.entities.Cliente;
import com.guiweber.estacionamento.jwt.JwtUserDetails;
import com.guiweber.estacionamento.repository.projection.ClienteProjection;
import com.guiweber.estacionamento.service.ClienteService;
import com.guiweber.estacionamento.service.UserService;
import com.guiweber.estacionamento.web.dto.ClienteCreateDto;
import com.guiweber.estacionamento.web.dto.ClienteResponseDto;
import com.guiweber.estacionamento.web.dto.PageableDto;
import com.guiweber.estacionamento.web.dto.UserResponseDto;
import com.guiweber.estacionamento.web.dto.mapper.ClienteMapper;
import com.guiweber.estacionamento.web.dto.mapper.PageableMapper;
import com.guiweber.estacionamento.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Clientes", description = "Endpoints de clientes")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UserService userService;

    @Operation(summary = "Create a new client", responses = {
            @ApiResponse(responseCode = "201", description = "Client created successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDto.class)
            )),
            @ApiResponse(responseCode = "409", description = "client already exists", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)
            )),
            @ApiResponse(responseCode = "422", description = "Invalid request data", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)
            )),
            @ApiResponse(responseCode = "403", description = "Resource error for ADMIN", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)
            ))
    })
    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> create(@RequestBody @Valid ClienteCreateDto dto,
                                                     @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        Cliente cliente = ClienteMapper.toCliente(dto);
        cliente.setUsuario(userService.findById(jwtUserDetails.getId()));
        clienteService.salvar(cliente);
        return ResponseEntity.status(201).body(ClienteMapper.toDto(cliente));
    }


    @Operation(summary = "Get client by id", responses = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDto.class)
            )),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)
            )),
            @ApiResponse(responseCode = "403", description = "Resource error for CLIENTE", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)
            ))
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClienteResponseDto> findById(@PathVariable Long id) {
        Cliente c = clienteService.findById(id);
        return ResponseEntity.ok(ClienteMapper.toDto(c));
    }

    @Operation(summary = "Get all clients",parameters = {
            @Parameter(in = ParameterIn.QUERY, name = "page", schema = @Schema(type = "integer", defaultValue = "0")
            , description = "Page number to retrieve (0-based)"),
            @Parameter(in = ParameterIn.QUERY, name = "size", schema = @Schema(type = "integer", defaultValue = "10"),
                    description = "Number of items per page"),
            @Parameter(in = ParameterIn.QUERY, name = "sort", schema = @Schema(type = "string", defaultValue = "id,asc"),
                    description = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    }, responses = {
            @ApiResponse(responseCode = "200", description = "Clients found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PageableDto.class)
            )),
            @ApiResponse(responseCode = "403", description = "Resource error for CLIENTE", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)
            ))
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> findAll(Pageable pageable) {
        Page<ClienteProjection> clientes = clienteService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(clientes));
    }
}
