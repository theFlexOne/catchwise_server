package com.flexone.catchwiseserver.mapper;

import com.flexone.catchwiseserver.domain.FishSpecies;
import com.flexone.catchwiseserver.dto.FishSpeciesDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FishSpeciesMapper {

    FishSpeciesDTO toDto(FishSpecies fishSpecies);
    List<FishSpeciesDTO> toDtoList(List<FishSpecies> fishSpecies);
    FishSpecies toEntity(FishSpeciesDTO fishSpeciesDTO);
    List<FishSpecies> toEntityList(List<FishSpeciesDTO> fishSpeciesDTO);

}
