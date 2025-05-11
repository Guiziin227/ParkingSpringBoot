package com.guiweber.estacionamento.web.dto.mapper;

import com.guiweber.estacionamento.entities.Cliente;
import com.guiweber.estacionamento.web.dto.ClienteCreateDto;
import com.guiweber.estacionamento.web.dto.ClienteResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {

    public static Cliente toCliente(ClienteCreateDto clienteCreateDto) {
        return new ModelMapper().map(clienteCreateDto, Cliente.class);
    }

    public static ClienteResponseDto toDto(Cliente cliente) {
        return new ModelMapper().map(cliente, ClienteResponseDto.class);
    }
}
