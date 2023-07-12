package com.flexone.catchwiseserver.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexone.catchwiseserver.bootstrap.json.PublicLakesJSON.PublicLakesPropertiesJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.wololo.geojson.FeatureCollection;
import org.wololo.jts2geojson.GeoJSONReader;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(3)
public class LakesSeed2 implements CommandLineRunner {

    private final static String pathName = "src/main/resources/data/public_lakes.geojson";
    private final static GeoJSONReader reader = new GeoJSONReader();
    private final static ObjectMapper mapper = new ObjectMapper();
    @Override
    public void run(String... args) throws Exception {
//        seed();
    }

    private void seed() throws IOException {
        FeatureCollection geojson = mapper.readValue(Paths.get(pathName).toFile(), FeatureCollection.class);
        Arrays.asList(geojson.getFeatures()).stream().forEach(feature -> {
            PublicLakesPropertiesJSON properties = mapper.convertValue(feature.getProperties(), PublicLakesPropertiesJSON.class);
        });
    }
}
