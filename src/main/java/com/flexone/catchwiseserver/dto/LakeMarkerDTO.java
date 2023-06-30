package com.flexone.catchwiseserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Target;
import org.locationtech.jts.geom.Point;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LakeMarkerDTO {

    private Long id;
    private String name;
    @Target(Point.class)
    @JsonProperty("coordinates")
    private Coordinates coordinates;
    public LakeMarkerDTO setCoordinates(Point geometry) {
        this.coordinates = new Coordinates()
                .setLatitude(String.valueOf(geometry.getY()))
                .setLongitude(String.valueOf(geometry.getX()));
        return this;
    }

    @Data
    @Accessors(chain = true)
    static
    class Coordinates {
        private String latitude;
        private String longitude;
    }

}
