package com.flexone.catchwiseserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flexone.catchwiseserver.util.LatLng;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Geometry;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LakeDTO {

    private static final String lakesUri = "http://localhost:8080/api/v1/lakes";

    private Long id;
    private String name;
    private String countyFips;
    private String localId;
    private String nearestTown;
    private LatLng coordinates = new LatLng();

    public LakeDTO setCoordinates(Double[] coordinates) {
        this.coordinates.setLat(coordinates[0]).setLng(coordinates[1]);
        return this;
    }

    public LakeDTO setCoordinates(Geometry geometry) {
        this.coordinates.setLat(geometry.getCoordinate().x).setLng(geometry.getCoordinate().y);
        return this;
    }

    public String getFishUrl() {
        return lakesUri + "/" + id + "/fish";
    }

}
