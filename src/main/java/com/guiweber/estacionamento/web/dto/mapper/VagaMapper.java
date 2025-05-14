package com.guiweber.estacionamento.web.dto.mapper;

import com.guiweber.estacionamento.entities.Vaga;
import com.guiweber.estacionamento.web.dto.VagaCreateDto;
import com.guiweber.estacionamento.web.dto.VagaResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {

    public static Vaga toVaga(VagaCreateDto vagaCreateDto) {
        return new ModelMapper().map(vagaCreateDto, Vaga.class);
    }

    public static VagaResponseDto toDto(Vaga vaga) {
        return new ModelMapper().map(vaga, VagaResponseDto.class);
    }
}
