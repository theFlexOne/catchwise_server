package com.flexone.catchwiseserver.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Geometry;

@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "geo_locations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class GeoLocation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "GEOMETRY", name = "geom")
  private Geometry geometry;
  @Column(length = 1000, name = "properties")
  private String properties = "{}";
}
