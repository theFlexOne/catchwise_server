package com.flexone.catchwiseserver.bootstrap.json;

import lombok.Data;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

import java.util.Map;

@Data
@Accessors(chain = true)
public class NewLakeGeometryJSON {
    private String type;
    private Map<String, Object> properties;
    private Double[][][][] coordinates;

    public MultiPolygon getCoordinates() {
        GeometryFactory geometryFactory = new GeometryFactory();
        Polygon[] polygons = new Polygon[coordinates.length];
        return geometryFactory.createMultiPolygon(polygons);
    }

}
