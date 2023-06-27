package com.flexone.catchwiseserver.bootstrap.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.*;
import org.wololo.geojson.Feature;
import org.wololo.geojson.GeoJSON;
import org.wololo.jts2geojson.GeoJSONReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewLakeJSON {

    private String localId;
    private String name;
    private String nearestTown;
    private String description;

    private List<FishJSON> fish;
    private List<ComponentJSON> components;
    private NewLakeGeometryJSON geometry;


    public MultiPolygon getGeometry() {
        return geometry.getCoordinates();
    }

    @Data
    public static class CoordinatesJSON {
        private double latitude;
        private double longitude;
    }
    @Data
    public static class FishJSON {
        private String name;
        private String species;
    }
    @Data
    public static class ComponentJSON {
        private String localId;
        private String name;
        private CoordinatesJSON coordinates;
        private List<FishJSON> fish;
    }
    @Data
    public static class GeometryJSON {
        private String type;
        private Map<String, Object> properties = new HashMap<>();
        private Polygon coordinates;
    }
}
