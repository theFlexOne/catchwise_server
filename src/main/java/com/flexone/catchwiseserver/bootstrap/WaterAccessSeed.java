package com.flexone.catchwiseserver.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexone.catchwiseserver.service.LakeService;
import com.flexone.catchwiseserver.service.WaterAccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.wololo.jts2geojson.GeoJSONReader;

import java.io.IOException;
import java.nio.file.*;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(3)
public class WaterAccessSeed implements CommandLineRunner {
    final static Path waterAccessPath = Paths.get("src", "main", "resources", "data", "water_access.geojson");

    final static ObjectMapper mapper = new ObjectMapper();
    final static GeoJSONReader reader = new GeoJSONReader();
    final static GeometryFactory geometryFactory = new GeometryFactory();

    final WaterAccessService waterAccessService;
    final LakeService lakeService;

    @Value("${app.seed.enabled:false}")
    private boolean seedEnabled;

    @Override
    public void run(String... args) throws Exception {
        if (!seedEnabled) return;
        seed();
    }

    private void seed() throws IOException {
//        FeatureCollection featureCollection = new ObjectMapper().readValue(waterAccessPath.toFile(), FeatureCollection.class);
//        log.info("featureCollection length: " + featureCollection.getFeatures().length);
//
//        List<WaterAccess> waterAccesses = new ArrayList<>();
//
//        Arrays.stream(featureCollection.getFeatures()).forEach(feature -> {
//            MapMarkerView mapMarkerType = locationService.findTypeById(2);
//            Point point = geometryFactory.createPoint(reader.read(feature.getGeometry()).getCoordinate());
//            Location location = new Location()
//                    .setGeometry(point)
//                    .setType(mapMarkerType.getType());
//
//            log.info(feature.getProperties().get("DOWLKNUM").toString());
//
//
//            Lake lake = lakeService.findByLocalId((String) feature.getProperties().get("DOWLKNUM"));
//
//            if (lake == null) {
//                log.info("lake is null");
//                log.info(feature.getProperties().get("DOWLKNUM").toString());
//                return;
//            }
//
//            Map<String, Object> properties = feature.getProperties();
//            WaterAccess waterAccess = new WaterAccess()
//                    .setLocation(location)
//                    .setLake(lake)
//                    .setName((String) properties.get("FAC_NAME"))
//                    .setComments((String) properties.get("COMMENTS"))
//                    .setDirections((String) properties.get("DIRECTIONS"))
//                    .setAdminDescription((String) properties.get("ADM_DESCRI"))
//                    .setLaunchType((String) properties.get("LAUNCHTYPE"))
//                    .setRampType((String) properties.get("RAMPTYPE"))
//                    .setNumberOfRamps((int) properties.get("NUMRAMPS"))
//                    .setNumberOfDocks((int) properties.get("NUMDOCKS"))
//                    .setNumberOfCars((int) properties.get("NUMCARS"))
//                    .setNumberOfCarTrailers((int) properties.get("NUMCARTRLR"))
//                    .setNumberOfADA((int) properties.get("NUMADA"))
//                    .setNumberOfToilets((int) properties.get("NUMTOILETS"))
//                    .setUniqueKey((String) properties.get("UNIQUEKEY"));
//
//            waterAccesses.add(waterAccess);
//        });
//
//        waterAccessService.saveAll(waterAccesses);

    }
}
