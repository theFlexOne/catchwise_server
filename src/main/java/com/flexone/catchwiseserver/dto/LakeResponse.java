package com.flexone.catchwiseserver.dto;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import lombok.Data;

@Data
public class LakeResponse {
    private Long id;
    private String name;
    private Point geometry;

    public void setGeometry(org.locationtech.jts.geom.Point geometry) {
        this.geometry = new GeometryFactory().createPoint(new com.vividsolutions.jts.geom.Coordinate(geometry.getX(), geometry.getY()));
    }
}
