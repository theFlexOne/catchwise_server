package com.flexone.catchwiseserver.dto;

import com.flexone.catchwiseserver.domain.MapMarker;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class MapMarkerDTO {
    Long id;
    String locationName;
    String markerType;
    Double[] coordinates = new Double[2];

    // region MAP TO DTO
    public static MapMarkerDTO from(MapMarker mapMarker) {
        Double[] coordinates = new Double[2];
        coordinates[0] = mapMarker.getGeometry().getCoordinate().getX();
        coordinates[1] = mapMarker.getGeometry().getCoordinate().getY();
        return new MapMarkerDTO()
                .setId(mapMarker.getId())
                .setMarkerType(mapMarker.getType())
                .setCoordinates(coordinates);
    }
    public static List<MapMarkerDTO> from(List<MapMarker> mapMarkers) {
        return mapMarkers.stream().map(MapMarkerDTO::from).toList();
    }
    // endregion
}
