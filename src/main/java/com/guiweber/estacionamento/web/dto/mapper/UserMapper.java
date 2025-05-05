package com.guiweber.estacionamento.web.dto.mapper;

import com.guiweber.estacionamento.entities.Usuario;
import com.guiweber.estacionamento.web.dto.UserCreateDto;
import com.guiweber.estacionamento.web.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {


    public static Usuario toUser(UserCreateDto createDto) {
        return new ModelMapper().map(createDto, Usuario.class);
    }

    public static UserResponseDto toDto(Usuario usuario) {
        String role = usuario.getRole().name().substring("ROLE_".length());
        PropertyMap<Usuario, UserResponseDto> props = new PropertyMap<Usuario, UserResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario, UserResponseDto.class);
    }

    public static List<UserResponseDto> toListDto(List<Usuario> usuarios) {
        return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }

}