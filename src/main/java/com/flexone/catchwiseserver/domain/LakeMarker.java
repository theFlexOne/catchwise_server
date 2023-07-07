package com.flexone.catchwiseserver.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Point;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "lakes_markers")
public class LakeMarker {

    @Id
    private Long id;
    private String name;
    private Point marker;

    public Double getLng() {
        return marker.getX();
    }
    public Double getLat() {
        return marker.getY();
    }
    public Double[] getCoords() {
        return new Double[]{getLng(), getLat()};
    }

}
