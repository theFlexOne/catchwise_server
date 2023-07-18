package com.flexone.catchwiseserver.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flexone.catchwiseserver.serializer.GeometrySerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LakeDTO {


    private Long id;
    private String name;
    private String localId;
    private String nearestTown;

    @Column(name = "geom", columnDefinition = "geometry(MultiPolygon,4326)")
    @JsonSerialize(using = GeometrySerializer.class)
    private MultiPolygon geometry;

    @Column(name = "marker", columnDefinition = "geometry(Point,4326)")
    @JsonSerialize(using = GeometrySerializer.class)
    private Geometry marker;

    private List<FishSpeciesDTO> fishSpecies = new ArrayList<>();

}
