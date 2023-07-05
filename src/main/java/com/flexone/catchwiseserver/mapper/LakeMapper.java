package com.flexone.catchwiseserver.mapper;

import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.dto.LakeDTO;
import com.flexone.catchwiseserver.dto.LakeNameDTO;
import com.flexone.catchwiseserver.repository.LakeNameProjection;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {FishSpeciesMapper.class})
@RequiredArgsConstructor
public abstract class LakeMapper {

    final static GeometryFactory geometryFactory = new GeometryFactory();

    @Autowired
    protected FishSpeciesMapper fishSpeciesMapper;

    public LakeDTO toDto(Lake lake) {
        return new LakeDTO()
                .setId(lake.getId())
                .setName(lake.getName())
                .setLocalId(lake.getLocalId())
                .setGeometry(lake.getGeometry())
                .setFishSpecies(fishSpeciesMapper.toDtoList(lake.getFishSpecies()));
    };

    public abstract List<LakeDTO> toDtoList(List<Lake> lakeList);

    public abstract Lake toEntity(LakeDTO lakeDTO);

    public abstract List<Lake> toEntityList(List<LakeDTO> lakeDTOList);
    
    public List<LakeNameDTO> toLakeNameDtoList(List<LakeNameProjection> lakeNames) {
        return lakeNames.stream().map(lake -> new LakeNameDTO(lake.getId(), lake.getName(), lake.getState(), lake.getCounty())).toList();
    }
}
