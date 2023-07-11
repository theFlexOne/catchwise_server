package com.flexone.catchwiseserver.domain;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flexone.catchwiseserver.serializer.GeometrySerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Entity
@Data
@Table(name = "map_markers")
@NoArgsConstructor
@Accessors(chain = true)
public class MapMarker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "map_marker_type_id")
    private MapMarkerType type = new MapMarkerType();

    @Column(columnDefinition = "GEOMETRY(POINT, 4326)", name = "geom")
    @JsonSerialize(using = GeometrySerializer.class)
    private Point geometry;

    public MapMarker(Long typeId, Point geometry) {
        type.setId(typeId);
        this.geometry = geometry;
    }

}
