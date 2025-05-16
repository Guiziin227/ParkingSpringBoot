package com.guiweber.estacionamento.web.dto.mapper;

import com.guiweber.estacionamento.entities.ClienteVaga;
import com.guiweber.estacionamento.web.dto.EstacionamentoCreateDto;
import com.guiweber.estacionamento.web.dto.EstacionamentoResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteVagaMapper {

    public static ClienteVaga toClienteVaga(EstacionamentoCreateDto estacionamentoCreateDto) {
        return new ModelMapper().map(estacionamentoCreateDto, ClienteVaga.class);
    }

    public static EstacionamentoResponseDto toDto(ClienteVaga clienteVaga) {
        return new ModelMapper().map(clienteVaga, EstacionamentoResponseDto.class);
    }
}
