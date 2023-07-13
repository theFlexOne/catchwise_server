package com.flexone.catchwiseserver.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexone.catchwiseserver.domain.County;
import com.flexone.catchwiseserver.domain.State;
import com.flexone.catchwiseserver.service.CountyService;
import com.flexone.catchwiseserver.service.StateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.jts2geojson.GeoJSONReader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class StatesAndCountiesSeed implements CommandLineRunner {

    final static Path allCountiesPath = Paths.get("src", "main", "resources", "data", "all_counties.geojson");
    final static Path allStatesPath = Paths.get("src", "main", "resources", "data", "all_states.geojson");

    final static ObjectMapper mapper = new ObjectMapper();
    final static GeoJSONReader reader = new GeoJSONReader();
    final static GeometryFactory geometryFactory = new GeometryFactory();
    final StateService stateService;
    final CountyService countyService;

    @Value("${app.seed.enabled:false}")
    private boolean seedEnabled;

    @Override
    public void run(String... args) throws Exception {
        if (!seedEnabled) return;
//        seed();
    }

    public void seed() throws IOException {
        FeatureCollection statesGeojson = new ObjectMapper().readValue(allStatesPath.toFile(), FeatureCollection.class);
        FeatureCollection countiesGeojson = new ObjectMapper().readValue(allCountiesPath.toFile(), FeatureCollection.class);

        Arrays.asList(countiesGeojson.getFeatures()).stream().forEach(countyFeature -> {
            State state = stateService.findByFips(countyFeature.getProperties().get("STATEFP").toString());
            if (state == null) {
                Feature stateFeature = Arrays.asList(statesGeojson.getFeatures()).stream()
                        .filter(feature -> feature.getProperties().get("STATEFP").toString().equals(countyFeature.getProperties().get("STATEFP").toString()))
                        .findFirst().orElse(null);
                if (stateFeature == null) {
                    log.error("State not found for county: " + countyFeature.getProperties().toString());
                    return;
                }
                MultiPolygon stateGeometry = (MultiPolygon) reader.read(stateFeature.getGeometry());

                state = new State()
                        .setName(stateFeature.getProperties().get("Name").toString())
                        .setAbbreviation(stateFeature.getProperties().get("STUSPS").toString())
                        .setFipsCode(stateFeature.getProperties().get("STATEFP").toString())
                        .setGnisId(stateFeature.getProperties().get("STATENS").toString())
                        .setGeometry(stateGeometry);

                stateService.save(state);
            }
            MultiPolygon countyGeometry = (MultiPolygon) reader.read(countyFeature.getGeometry());

            County county = new County()
                    .setName(countyFeature.getProperties().get("Name").toString())
                    .setFips(countyFeature.getProperties().get("COUNTYFP").toString())
                    .setGnisId(countyFeature.getProperties().get("COUNTYNS").toString())
                    .setState(state)
                    .setGeometry(countyGeometry);

            countyService.save(county);
        });


    }
}
