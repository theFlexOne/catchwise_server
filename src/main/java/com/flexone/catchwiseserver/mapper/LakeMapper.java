package com.flexone.catchwiseserver.mapper;

import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.dto.LakeDTO;
import com.flexone.catchwiseserver.dto.LakeMarkerDTO;
import com.flexone.catchwiseserver.repository.LakeMarkerProjection;
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
    }

    ;

    public abstract List<LakeDTO> toDtoList(List<Lake> lakeList);

    public abstract Lake toEntity(LakeDTO lakeDTO);

    public abstract List<Lake> toEntityList(List<LakeDTO> lakeDTOList);

    public List<LakeMarkerDTO> toLakeMarkerDtoList(List<LakeMarkerProjection> lakeMarkers) {
        return lakeMarkers.stream().map(lake -> {
            LakeMarkerDTO lakeMarkerDTO = new LakeMarkerDTO();
            double[] coordinates = new double[2];
            coordinates[0] = lake.getMarker().getX();
            coordinates[1] = lake.getMarker().getY();

            lakeMarkerDTO.setLakeId(lake.getLakeId());
            lakeMarkerDTO.setLakeName(lake.getLakeName());
            lakeMarkerDTO.setCountyName(lake.getCountyName());
            lakeMarkerDTO.setStateName(lake.getStateName());
            lakeMarkerDTO.setCoordinates(coordinates);

            return lakeMarkerDTO;
        }).toList();
    }
}
