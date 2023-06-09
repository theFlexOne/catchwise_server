package com.flexone.catchwiseserver.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "lakes")
public class Lake {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "county_fips")
  private String countyFips;

  @Column(name = "local_id")
  private String localId;

  @Column(name = "nearest_town")
  private String nearestTown;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinTable(
          name = "lake_geo_locations",
          joinColumns = @JoinColumn(name = "lake_id"),
          inverseJoinColumns = @JoinColumn(name = "geo_location_id")
  )
  private GeoLocation geoLocation;



}
