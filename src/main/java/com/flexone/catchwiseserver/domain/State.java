package com.flexone.catchwiseserver.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "states")
public class State {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "abbreviation")
  private String abbreviation;

  @Column(name = "fips")
  private String fipsCode;

  @Column(name = "ansi")
  private String ansiCode;

  @OneToMany(
          mappedBy = "state",
          cascade = CascadeType.ALL,
          orphanRemoval = true
  )
  private List<County> counties = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL)
  @JoinTable(
          name = "state_geo_location",
          joinColumns = @JoinColumn(name = "state_id"),
          inverseJoinColumns = @JoinColumn(name = "geo_location_id")
          )
  GeoLocation geoLocation;

  public State addCounties(List<County> countyList) {
    this.counties.addAll(countyList);
    countyList.forEach(county -> county.setState(this));
    return this;
  }



}
