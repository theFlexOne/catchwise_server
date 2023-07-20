package com.flexone.catchwiseserver.dto;

import com.flexone.catchwiseserver.domain.MapMarker;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Accessors(chain = true)
public class MapMarkerDTO {

    private Long id;
    private String name;
    private Double[] coordinates = new Double[2];
    private String type;

    public static MapMarkerDTO mapToDTO(MapMarker mapMarker) {
        return new MapMarkerDTO()
                .setId(mapMarker.getId())
                .setName(mapMarker.getName())
                .setCoordinates(new Double[]{mapMarker.getGeom().getX(), mapMarker.getGeom().getY()})
                .setType(mapMarker.getType());
    }
    public static List<MapMarkerDTO> mapToDTO(List<MapMarker> mapMarkerList) {
        return mapMarkerList.stream().map(MapMarkerDTO::mapToDTO).toList();
    }
}
