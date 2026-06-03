package com.serverside.dt.mappers;

import org.mapstruct.Mapper;

import com.serverside.dt.dtos.DividendFrequencyDTO;
import com.serverside.dt.entities.DividendFrequency;

@Mapper(componentModel = "spring")
public interface DividendFrequencyMapper {
    DividendFrequencyDTO toDTO(DividendFrequency entity);
    DividendFrequency toEntity(DividendFrequencyDTO dto);
}
