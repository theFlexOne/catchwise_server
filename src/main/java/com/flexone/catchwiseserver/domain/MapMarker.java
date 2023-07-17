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
@Table(name = "all_map_locations")
public class MapMarker {

    @Id
    private Long id;
    @Column(name = "location_name")
    private String locationName;
    @Column(name = "marker_type")
    private String type;

    @JsonSerialize(using = GeometrySerializer.class)
    @Column(columnDefinition = "GEOMETRY(POINT, 4326)", name = "marker")
    private Point geometry;
}
