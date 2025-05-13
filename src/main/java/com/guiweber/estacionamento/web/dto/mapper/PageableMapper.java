package com.guiweber.estacionamento.web.dto.mapper;

import com.guiweber.estacionamento.web.dto.PageableDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {

    public static PageableDto toDto(Page page) {
        return new ModelMapper().map(page, PageableDto.class);
    }
}
