package com.serverside.dt.mappers;

import org.mapstruct.Mapper;

import com.serverside.dt.dtos.SectorDTO;
import com.serverside.dt.entities.Sector;

@Mapper(componentModel = "spring")
public interface SectorMapper {
    SectorDTO toDTO(Sector entity);
    Sector toEntity(SectorDTO dto);
}
