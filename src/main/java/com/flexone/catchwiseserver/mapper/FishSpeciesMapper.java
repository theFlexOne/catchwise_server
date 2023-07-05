package com.flexone.catchwiseserver.mapper;

import com.flexone.catchwiseserver.domain.FishSpecies;
import com.flexone.catchwiseserver.dto.FishSpeciesDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FishSpeciesMapper {

    public abstract FishSpeciesDTO toDto(FishSpecies fishSpecies);
    public abstract List<FishSpeciesDTO> toDtoList(List<FishSpecies> fishSpeciesList);

    public abstract FishSpecies toEntity(FishSpeciesDTO fishSpeciesDTO);
    public abstract List<FishSpecies> toEntityList(List<FishSpeciesDTO> fishSpeciesDTOList);

}
