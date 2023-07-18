package com.flexone.catchwiseserver.dto;

import com.flexone.catchwiseserver.domain.Location;
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
    public static MapMarkerDTO from(Location location) {
        Double[] coordinates = new Double[2];
        coordinates[0] = location.getGeometry().getCoordinate().getX();
        coordinates[1] = location.getGeometry().getCoordinate().getY();
        return new MapMarkerDTO()
                .setId(location.getId())
                .setMarkerType(location.getType())
                .setCoordinates(coordinates);
    }
    public static List<MapMarkerDTO> from(List<Location> locations) {
        return locations.stream().map(MapMarkerDTO::from).toList();
    }
    // endregion
}
