package com.flexone.catchwiseserver.repository;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flexone.catchwiseserver.serializer.GeometrySerializer;
import org.locationtech.jts.geom.Point;

import javax.persistence.Column;

public interface LakeMarkerProjection {
    long getLakeId();
    String getLakeName();
    String getCountyName();
    String getStateName();
    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(using = GeometryDeserializer.class)
    @Column(columnDefinition = "geometry(Point,4326)")
    Point getMarker();
}
