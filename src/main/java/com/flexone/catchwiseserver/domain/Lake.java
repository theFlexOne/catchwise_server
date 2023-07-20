package com.flexone.catchwiseserver.domain;


import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flexone.catchwiseserver.serializer.GeometrySerializer;
import lombok.*;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "lakes")
public class Lake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "local_id")
    private String localId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "county_id")
    private County county;


    @Column(columnDefinition = "GEOMETRY(MULTIPOLYGON, 4326)", name = "geom")
    @JsonSerialize(using = GeometrySerializer.class)
    private MultiPolygon geometry;

    @Column(columnDefinition = "GEOMETRY(POINT, 4326)", name = "map_marker")
    @JsonSerialize(using = GeometrySerializer.class)
    private Point mapMarker;

    @OneToMany(mappedBy = "lake", cascade = CascadeType.MERGE)
    private List<WaterAccess> waterAccessList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "lakes_fish_species",
            joinColumns = @JoinColumn(name = "lake_id"),
            inverseJoinColumns = @JoinColumn(name = "fish_species_id"))
    @Column(name = "fish_species")
    private List<FishSpecies> fishSpecies = new ArrayList<>();

    public Lake addFishSpecies(FishSpecies fishSpecies) {
        this.fishSpecies.add(fishSpecies);
        return this;
    }




}
