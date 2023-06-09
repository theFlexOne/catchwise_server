package com.flexone.catchwiseserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Coordinates;

import java.util.List;


@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LakeJSON {

  String name;
  String countyFips;
  String localId;
  String nearestTown;
  LatLng latLng;

  @JsonProperty("fish")
  List<FishSpecies> fishSpecies;

  List<Component> components;

@Data
private static class LatLng {
    @JsonProperty("latitude")
    double lat;
    @JsonProperty("longitude")
    double lng;
  }

  @Data
  private static class FishSpecies {
    String name;
    String species;
  }

  @Data
  private static class Component {
    String localId;
    String name;
    Coordinates coordinates;

    @JsonProperty("fish")
    List<FishSpecies> fishSpecies;
  }
}
