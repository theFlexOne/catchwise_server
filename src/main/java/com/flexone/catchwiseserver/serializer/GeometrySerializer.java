package com.flexone.catchwiseserver.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.locationtech.jts.geom.*;
import org.springframework.core.serializer.Serializer;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class GeometrySerializer extends StdSerializer<Geometry> {

    final GeoJSONWriter writer = new GeoJSONWriter();

    public GeometrySerializer() {
        this(null);
    }
    public GeometrySerializer(Class<Geometry> t) {
        super(t);
    }

    @Override
    public void serialize(Geometry value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        org.wololo.geojson.Geometry geometry = writer.write(value);
        gen.writeObject(geometry);
    }

    public void writeGeometry(JsonGenerator jgen, Geometry value) throws IOException {
        if (value instanceof Polygon) {
            this.writePolygon(jgen, (Polygon) value);
        } else if (value instanceof Point) {
            this.writePoint(jgen, (Point) value);
        } else if (value instanceof MultiPoint) {
            this.writeMultiPoint(jgen, (MultiPoint) value);
        } else if (value instanceof MultiPolygon) {
            this.writeMultiPolygon(jgen, (MultiPolygon) value);
        } else if (value instanceof LineString) {
            this.writeLineString(jgen, (LineString) value);
        } else if (value instanceof MultiLineString) {
            this.writeMultiLineString(jgen, (MultiLineString) value);
        } else {
            if (!(value instanceof GeometryCollection)) {
                throw new JsonMappingException("Geometry type " + value.getClass().getName() + " cannot be serialized as GeoJSON." + "Supported types are: " + Arrays.asList(Point.class.getName(), LineString.class.getName(), Polygon.class.getName(), MultiPoint.class.getName(), MultiLineString.class.getName(), MultiPolygon.class.getName(), GeometryCollection.class.getName()));
            }

            this.writeGeometryCollection(jgen, (GeometryCollection) value);
        }

    }
    private void writeGeometryCollection(JsonGenerator jgen, GeometryCollection value) throws IOException {
        org.wololo.geojson.Geometry json = writer.write(value);
        String jsonString = json.toString();
        jgen.writeRawValue(jsonString);
    }

    private void writeMultiPoint(JsonGenerator jgen, MultiPoint value) throws IOException {
        org.wololo.geojson.Geometry json = writer.write(value);
        jgen.writeRawValue(json.toString());
    }

    private void writeMultiLineString(JsonGenerator jgen, MultiLineString value) throws IOException {
        org.wololo.geojson.Geometry json = writer.write(value);
        jgen.writeRawValue(json.toString());
    }

    public Class<Geometry> handledType() {
        return Geometry.class;
    }

    private void writeMultiPolygon(JsonGenerator jgen, MultiPolygon value) throws IOException {
        org.wololo.geojson.Geometry json = writer.write(value);
    }

    private void writePolygon(JsonGenerator jgen, Polygon value) throws IOException {
        org.wololo.geojson.Geometry json = writer.write(value);
        jgen.writeRawValue(json.toString());

    }

    private void writePolygonCoordinates(JsonGenerator jgen, Polygon value) throws IOException {
        org.wololo.geojson.Geometry json = writer.write(value);
        jgen.writeRawValue(json.toString());

    }


    private void writeLineStringCoords(JsonGenerator jgen, LineString ring) throws IOException {
        org.wololo.geojson.Geometry json = writer.write(ring);
        jgen.writeRawValue(json.toString());
    }

    private void writeLineString(JsonGenerator jgen, LineString lineString) throws IOException {
        org.wololo.geojson.Geometry json = writer.write(lineString);
        jgen.writeRawValue(json.toString());
    }

    private void writePoint(JsonGenerator jgen, Point p) throws IOException {
        org.wololo.geojson.Geometry json = writer.write(p);
        jgen.writeRawValue(json.toString());
    }

    private void writePointCoords(JsonGenerator jgen, Point p) throws IOException {
        org.wololo.geojson.Geometry json = writer.write(p);
        jgen.writeRawValue(json.toString());
    }

}
