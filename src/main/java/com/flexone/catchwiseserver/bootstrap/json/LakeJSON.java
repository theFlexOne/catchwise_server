package com.flexone.catchwiseserver.bootstrap.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Coordinates;
import org.locationtech.jts.geom.MultiPolygon;
import org.wololo.geojson.Feature;

import java.util.List;


@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class LakeJSON {

  @Data
  public static class FishSpecies {
    String name;
    String scientificName;
  }

  @Data
  public static class Geometry {
    String type = "MultiPolygon";
    List<List<List<Double>>> coordinates;
  }

  String id;
  String name;
  Long gnisId;
  String county;
  String nearestTown;
  List<FishSpecies> fishSpecies;
  @JsonProperty("geoJson")
  Feature feature;
  Double shorelineLength;
  Double area;
  Double maxDepth;
}
