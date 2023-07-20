package com.flexone.catchwiseserver.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flexone.catchwiseserver.serializer.GeometrySerializer;
import lombok.Data;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Accessors(chain = true)
@Entity
public class MapMarker {

    @Id
    @Column(name = "id")
    protected Long id;

    @Column(name = "name")
    private String name;

    @Column(columnDefinition = "GEOMETRY(POINT, 4326)", name = "geom")
    @JsonSerialize(using = GeometrySerializer.class)
    private Point geom;

    @Column(name = "type")
    private String type;
}
