package com.flexone.catchwiseserver.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Immutable;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lake_markers")
@Immutable
@Accessors(chain = true)
public class LakeMarker {

    @Id
    @Column(name = "lake_id")
    private Long lakeId;

    @Column(name = "lake_name")
    private String lakeName;

    @Column(name = "geom", columnDefinition = "geometry(Point,4326)")
    private Point geometry;

}
