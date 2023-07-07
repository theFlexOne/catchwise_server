package com.flexone.catchwiseserver.repository;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flexone.catchwiseserver.serializer.GeometrySerializer;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

import javax.persistence.Column;

public interface LakeMarkerView {

    Long getLakeId();
    String getLakeName();
    @Column(columnDefinition = "POINT")
    @JsonSerialize(using = GeometrySerializer.class)
    Point getMarker();
}
