package com.flexone.catchwiseserver.domain;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flexone.catchwiseserver.serializer.GeometrySerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@NoArgsConstructor
@Accessors(chain = true)
@Data
@Entity
@Table(name = "locations")
public class Location {

    @Id
    private Long id;

    @Column(name = "type")
    private String type;

    @JsonSerialize(using = GeometrySerializer.class)
    @Column(columnDefinition = "GEOMETRY(POINT, 4326)", name = "geom")
    private Point geometry;
}