package com.flexone.catchwiseserver.mapper;

import com.flexone.catchwiseserver.domain.LakeMarker;
import com.flexone.catchwiseserver.dto.LakeMarkerDTO;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class LakeMarkerMapper {

    private static final GeometryFactory geometryFactory = new GeometryFactory();

    public LakeMarkerDTO toDto(LakeMarker lakeMarker) {
        return new LakeMarkerDTO(
                lakeMarker.getLakeId(),
                lakeMarker.getLakeName(),
                new double[]{lakeMarker.getGeometry().getX(), lakeMarker.getGeometry().getY()},
                0
        );
    }

    public List<LakeMarkerDTO> toDtoList(List<LakeMarker> lakeMarkers) {
        return lakeMarkers.stream().map(lakeMarker -> toDto(lakeMarker)).toList();
    }
}
