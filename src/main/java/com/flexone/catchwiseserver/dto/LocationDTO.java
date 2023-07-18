package com.flexone.catchwiseserver.dto;

import com.flexone.catchwiseserver.domain.Location;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class LocationDTO {
    Long id;
    String type;
    Double[] coordinates = new Double[2];

    // region MAP TO DTO
    public static LocationDTO from(Location location) {
        Double[] coordinates = new Double[2];
        coordinates[0] = location.getGeometry().getCoordinate().getX();
        coordinates[1] = location.getGeometry().getCoordinate().getY();
        return new LocationDTO()
                .setId(location.getId())
                .setType(location.getType())
                .setCoordinates(coordinates);
    }
    public static List<LocationDTO> from(List<Location> locations) {
        return locations.stream().map(LocationDTO::from).toList();
    }
    // endregion
}
