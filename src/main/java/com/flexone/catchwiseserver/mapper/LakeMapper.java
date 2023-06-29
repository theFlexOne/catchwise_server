package com.flexone.catchwiseserver.mapper;

import com.flexone.catchwiseserver.domain.FishSpecies;
import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.dto.LakeDTO;
import com.flexone.catchwiseserver.dto.LakeFishResponseDTO;
import com.flexone.catchwiseserver.dto.LakeMarkerDTO;
import com.flexone.catchwiseserver.dto.LakeShortDTO;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {FishSpeciesMapper.class})
public abstract class LakeMapper {

    public LakeDTO toDto(Lake lake) {
        return new LakeDTO()
                .setId(lake.getId())
                .setName(lake.getName());
    };
    public abstract List<LakeDTO> toDtoList(List<Lake> lakeList);

    public abstract Lake toEntity(LakeDTO lakeDTO);
    public abstract List<Lake> toEntityList(List<LakeDTO> lakeDTOList);



    public LakeMarkerDTO toMarkerDto(Lake lake) {
        return new LakeMarkerDTO()
                .setId(lake.getId())
                .setName(lake.getName());
    }

    public List<LakeMarkerDTO> toMarkerDtoList(List<Lake> lakesList) {
        return lakesList.stream().map(this::toMarkerDto).toList();
    }

}
