package com.guiweber.estacionamento.web.dto.mapper;

import com.guiweber.estacionamento.entities.User;
import com.guiweber.estacionamento.entities.enums.Role;
import com.guiweber.estacionamento.web.dto.UserCreateDto;
import com.guiweber.estacionamento.web.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static User toUser(UserCreateDto userCreateDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userCreateDto, User.class);
    }

    public static UserResponseDto toDto(User user) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Mapear os campos básicos (id, username)
        UserResponseDto dto = modelMapper.map(user, UserResponseDto.class);

        // Converter o role manualmente
        String role = user.getRole().toString().replace("ROLE_", "");
        dto.setRole(role);

        return dto;
    }

    public static List<UserResponseDto> toUsers(List<User> users) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        List<UserResponseDto> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(toDto(user));
        }
        return dtos;
    }
}