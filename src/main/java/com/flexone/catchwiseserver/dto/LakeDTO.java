package com.flexone.catchwiseserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flexone.catchwiseserver.domain.FishSpecies;
import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.serializer.GeometrySerializer;
import com.flexone.catchwiseserver.util.LatLng;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

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

    private List<FishSpeciesDTO> fishSpecies = new ArrayList<>();

}
