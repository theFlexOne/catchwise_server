package com.flexone.catchwiseserver.domain;


import javax.persistence.*;

import lombok.*;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import java.util.HashSet;
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
  private MultiPolygon geometry;

  @ManyToOne(fetch = FetchType.LAZY)
  private County county;


  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinTable(name = "lakes_fish_species",
          joinColumns = @JoinColumn(name = "fish_species_id"),
          inverseJoinColumns = @JoinColumn(name = "lake_id"))
  @Column(name = "fish_species")
  private Set<FishSpecies> fishSpecies = new HashSet<>();

  public Lake addFishSpecies(FishSpecies fishSpecies) {
    this.fishSpecies.add(fishSpecies);
    return this;
  }
}
