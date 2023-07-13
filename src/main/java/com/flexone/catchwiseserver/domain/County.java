package com.flexone.catchwiseserver.domain;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;

import java.util.List;

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
  private String fips;

  @Column(name = "ansi")
  private String ansiCode;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @JoinColumn(name = "state_id", nullable = false)
  private State state;

  @Column(columnDefinition = "GEOMETRY(MULTIPOLYGON, 4326)", name = "geom")
  @JsonSerialize(using = GeometrySerializer.class)
  @JsonDeserialize(using = GeometryDeserializer.class)
  private MultiPolygon geometry;

}
