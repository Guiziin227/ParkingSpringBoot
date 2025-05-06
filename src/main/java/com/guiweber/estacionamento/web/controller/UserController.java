package com.guiweber.estacionamento.web.controller;

import com.guiweber.estacionamento.entities.Usuario;
import com.guiweber.estacionamento.service.UserService;
import com.guiweber.estacionamento.web.dto.UserCreateDto;
import com.guiweber.estacionamento.web.dto.UserPasswordDto;
import com.guiweber.estacionamento.web.dto.UserResponseDto;
import com.guiweber.estacionamento.web.dto.mapper.UserMapper;
import com.guiweber.estacionamento.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "User", description = "Endpoints for user management")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a new user",responses = {
            @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDto.class)
            )),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)
            )),
            @ApiResponse(responseCode = "422", description = "Invalid request data", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)
            ))
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        Usuario u = userService.save(UserMapper.toUser(userCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(u));
    }


    @Operation(summary = "Get user by id ",responses = {
           @ApiResponse(responseCode = "200", description = "User found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDto.class)
            )),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)
            ))
    })
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR (hasRole('CLIENTE') AND authentication.principal.id == #id)")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        Usuario u = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDto(u));
    }

    @Operation(summary = "Update user password",responses = {
            @ApiResponse(responseCode = "204", description = "Password updated successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDto.class)
            )),
            @ApiResponse(responseCode = "400", description = "Current password not match", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)
            )),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)
            ))
    })
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,@Valid @RequestBody UserPasswordDto user) {
        Usuario u = userService.editPassword(id, user.getPassword(),user.getNewPassword(),user.getConfirmPassword());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Get all users",responses = {
            @ApiResponse(responseCode = "200", description = "Users found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDto.class)
            ))
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<Usuario> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toListDto(users));
    }

}
