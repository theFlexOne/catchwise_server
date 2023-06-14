package com.flexone.catchwiseserver.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

import java.io.IOException;

public class GeometrySerializer extends StdSerializer {
    final GeometryFactory geometryFactory = new GeometryFactory();

    public GeometrySerializer() {
        super(String.class);
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value instanceof Point) {
            Point point = (Point) value;
            gen.setCurrentValue(point);
            gen.writeArrayFieldStart("coordinates");
            gen.writeNumber(point.getX());
            gen.writeNumber(point.getY());
            gen.writeEndArray();
        } else if (value instanceof MultiPolygon) {
            MultiPolygon multiPolygon = (MultiPolygon) value;
            gen.writeArrayFieldStart("coordinates");
            for (int i = 0; i < multiPolygon.getNumGeometries(); i++) {
                Geometry geometry = multiPolygon.getGeometryN(i);
                gen.writeStartArray();
                for (int j = 0; j < geometry.getNumGeometries(); j++) {
                    Point point = (Point) geometry.getGeometryN(j);
                    gen.writeStartArray();
                    gen.writeNumber(point.getX());
                    gen.writeNumber(point.getY());
                    gen.writeEndArray();
                }
                gen.writeEndArray();
            }
            gen.writeEndArray();
        }
    }

}
