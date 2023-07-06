package com.flexone.catchwiseserver.mapper;

import com.flexone.catchwiseserver.domain.LakeMarker;
import com.flexone.catchwiseserver.dto.LakeMarkerDTO;
import com.flexone.catchwiseserver.repository.LakeMarkerProjection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class LakeMarkerMapper {

    private static final GeometryFactory geometryFactory = new GeometryFactory();

    public LakeMarkerDTO toDto(LakeMarkerProjection lakeMarker) {
        LakeMarkerDTO lakeMarkerDTO = new LakeMarkerDTO();
        double[] coordinates = new double[2];
        coordinates[0] = lakeMarker.getMarker().getX();
        coordinates[1] = lakeMarker.getMarker().getY();

        lakeMarkerDTO.setLakeId(lakeMarker.getLakeId());
        lakeMarkerDTO.setLakeName(lakeMarker.getLakeName());
        lakeMarkerDTO.setCountyName(lakeMarker.getCountyName());
        lakeMarkerDTO.setStateName(lakeMarker.getStateName());
        lakeMarkerDTO.setCoordinates(coordinates);

        return lakeMarkerDTO;
    }

    public List<LakeMarkerDTO> toDtoList(List<LakeMarkerProjection> lakeMarkers) {
        return lakeMarkers.stream().map(lakeMarker -> toDto(lakeMarker)).toList();
    }
}
