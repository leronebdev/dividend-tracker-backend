package com.serverside.dt.mappers;

import com.serverside.dt.dtos.DividendEventDTO;
import com.serverside.dt.entities.DividendEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DividendEventMapper {

    DividendEventDTO toDTO(DividendEvent entity);

    DividendEvent toEntity(DividendEventDTO dto);
}