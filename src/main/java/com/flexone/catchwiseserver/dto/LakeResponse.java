package com.flexone.catchwiseserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LakeResponse {

    private static final String lakesUri = "http://localhost:8080/api/v1/lakes";

    private Long id;
    private String name;
    private Coordinates coordinates = new Coordinates(0.0, 0.0);

    public LakeResponse setCoordinates(Double[] coordinates) {
        this.coordinates.setLatitude(coordinates[0]);
        this.coordinates.setLongitude(coordinates[1]);
        return this;
    }

    public String getFishUrl() {
        return lakesUri + "/" + id + "/fish";
    }

}

@Data
@Accessors(chain = true)
class Coordinates {
    private Double latitude;
    private Double longitude;

    public Coordinates(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
