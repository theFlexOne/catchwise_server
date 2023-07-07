package com.flexone.catchwiseserver.mapper;

import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.dto.LakeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.GeometryFactory;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {FishSpeciesMapper.class})
@RequiredArgsConstructor
@Slf4j
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

}
