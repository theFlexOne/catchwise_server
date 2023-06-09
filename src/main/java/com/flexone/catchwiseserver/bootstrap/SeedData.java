package com.flexone.catchwiseserver.bootstrap;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.flexone.catchwiseserver.domain.County;
import com.flexone.catchwiseserver.domain.GeoLocation;
import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.domain.State;
import com.flexone.catchwiseserver.dto.LakeJSON;
import com.flexone.catchwiseserver.repository.StateRepository;
import com.flexone.catchwiseserver.service.LakeService;
import com.flexone.catchwiseserver.service.StateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.jts2geojson.GeoJSONReader;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedData implements CommandLineRunner {

  private final StateService stateService;
  private final LakeService lakeService;
  private final GeoJSONReader reader = new GeoJSONReader();

  @Override
  public void run(String... args) throws Exception {
    log.info("Loading seed data...");
    seedStates("src/main/resources/data/US_States.geojson");
    seedLakes("src/main/resources/data/MN_Lakes_Local_Data.json");
    log.info("Finished loading seed data.");
  }

  private void seedStates(String pathName) throws IOException {
    FeatureCollection statesFeatureCollection = importFeatureCollection(pathName);
    FeatureCollection countyFeatureCollection = importFeatureCollection("src/main/resources/data/US_Counties.geojson");

    Feature[] features = statesFeatureCollection.getFeatures();
    List<State> statesList = new ArrayList<>();
    for (Feature feature : features) {
      Map<String, Object> properties = feature.getProperties();
      Geometry geometry = reader.read(feature.getGeometry());
      List<County> countyList = new ArrayList<>();
      for (Feature countyFeature : countyFeatureCollection.getFeatures()) {
        Map<String, Object> countyProperties = countyFeature.getProperties();
        Geometry countyGeometry = reader.read(countyFeature.getGeometry());
        if (feature.getProperties().get("STATEFP").equals(countyProperties.get("STATEFP"))) {
          County newCounty = new County()
                  .setName((String) countyProperties.get("NAME"))
                  .setFipsCode((String) countyProperties.get("COUNTYFP"))
                  .setAnsiCode((String) countyProperties.get("COUNTYNS"))
                  .setGeoLocation(new GeoLocation().setGeometry(countyGeometry).setProperties(new ObjectMapper().writeValueAsString(countyProperties)));
          countyList.add(newCounty);
        }
      }
      State newState = new State()
              .setName((String) properties.get("NAME"))
              .setAbbreviation((String) properties.get("STUSPS"))
              .setFipsCode((String) properties.get("STATEFP"))
              .setAnsiCode((String) properties.get("STATENS"))
              .setGeoLocation(new GeoLocation()
                      .setGeometry(geometry)
                      .setProperties(new ObjectMapper().writeValueAsString(properties)))
              .addCounties(countyList);

      statesList.add(newState);
    }
    log.info("Saving States data...");
    stateService.saveAll(statesList);
  }

  private void seedLakes(String pathName) throws IOException {
    CollectionType collectionType = new ObjectMapper()
            .getTypeFactory().constructCollectionType(List.class, LakeJSON.class);
    List<LakeJSON> lakeJSONList = new ObjectMapper().readValue(Paths.get(pathName).toFile(), collectionType);
    List<Lake> lakeList = mapLakeJSONListToLakeList(lakeJSONList);
    log.info("Saving Lakes data...");
    lakeService.saveAll(lakeList);

  }

  private List<Lake> mapLakeJSONListToLakeList(List<LakeJSON> lakeJSONList) {
    List<Lake> lakeList = new ArrayList<>();
    for (LakeJSON lakeJSON : lakeJSONList) {
      Coordinate coordinate = new Coordinate(lakeJSON.getCoordinates().getLng(), lakeJSON.getCoordinates().getLat());
      Point point = new Point(coordinate, null, 0);
      lakeList.add(new Lake()
              .setName(lakeJSON.getName())
              .setGeoLocation(new GeoLocation()
                      .setGeometry(point)));
    }
    return lakeList;
  }

  private Geometry coordinatesToGeometry(Coordinate coordinate) {
    return reader.read("{" +
            "\"type\": \"Point\"," +
            "\"coordinates\": [" + coordinate.x + ", " + coordinate.y + "]" +
            "}");
  }

  private FeatureCollection importFeatureCollection(String pathName) throws IOException {
    return new ObjectMapper().readValue(Paths.get(pathName).toFile(), FeatureCollection.class);
  }


}
