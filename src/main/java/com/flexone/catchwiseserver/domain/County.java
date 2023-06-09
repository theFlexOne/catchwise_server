package com.flexone.catchwiseserver.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "counties")
public class County {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "fips")
  private String fipsCode;

  @Column(name = "ansi")
  private String ansiCode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "state_id", nullable = false)
  private State state;


  @OneToOne(cascade = CascadeType.ALL)
  @JoinTable(
          name = "county_geo_locations",
          joinColumns = @JoinColumn(name = "county_id"),
          inverseJoinColumns = @JoinColumn(name = "geo_location_id")
  )
  GeoLocation geoLocation;
}
