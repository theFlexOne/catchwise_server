package com.flexone.catchwiseserver.domain;


import javax.persistence.*;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

  @Column(name = "nearest_town")
  private String nearestTown;

  @Column(columnDefinition = "GEOMETRY(MULTIPOLYGON, 4326)", name = "geom")
  @JsonSerialize(using = GeometrySerializer.class)
  @JsonDeserialize(using = GeometryDeserializer.class)
  private MultiPolygon geometry;

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
