package com.flexone.catchwiseserver.bootstrap;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexone.catchwiseserver.domain.County;
import com.flexone.catchwiseserver.domain.GeoLocation;
import com.flexone.catchwiseserver.domain.State;
import com.flexone.catchwiseserver.repository.StateRepository;
import com.flexone.catchwiseserver.service.LakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Geometry;
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

  private final StateRepository stateRepository;
  private final LakeService lakeService;
  private final GeoJSONReader reader = new GeoJSONReader();

  @Override
      public void run(String... args) throws Exception {
          log.info("Loading seed data...");
          seedStates("src/main/resources/json/US_States.geojson");

      }

  private void seedStates(String pathName) throws IOException {
    FeatureCollection statesFeatureCollection = importFeatureCollection(pathName);
    FeatureCollection countyFeatureCollection = importFeatureCollection("src/main/resources/json/US_Counties.geojson");

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
    stateRepository.saveAll(statesList);
  }

  private FeatureCollection importFeatureCollection(String pathName) throws IOException {
    return new ObjectMapper().readValue(Paths.get(pathName).toFile(), FeatureCollection.class);
  }



}
