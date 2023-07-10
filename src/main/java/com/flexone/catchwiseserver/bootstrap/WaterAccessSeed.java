package com.flexone.catchwiseserver.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexone.catchwiseserver.bootstrap.json.WaterAccessJSONProperties;
import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.domain.MapMarker;
import com.flexone.catchwiseserver.domain.MapMarkerType;
import com.flexone.catchwiseserver.domain.WaterAccess;
import com.flexone.catchwiseserver.service.LakeService;
import com.flexone.catchwiseserver.service.MapMarkerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.wololo.geojson.FeatureCollection;
import org.wololo.jts2geojson.GeoJSONReader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class WaterAccessSeed implements CommandLineRunner {

    final static ObjectMapper mapper = new ObjectMapper();
    final static GeometryFactory geometryFactory = new GeometryFactory();
    static final GeoJSONReader reader = new GeoJSONReader();

    final MapMarkerService mapMarkerService;
    final LakeService lakeService;

        @Override
        public void run(String... args) throws Exception {
            FeatureCollection featureCollection = importFeatureCollection("src/main/resources/data/public_water_access.geojson");
            Arrays.asList(featureCollection.getFeatures()).forEach(feature -> {
                WaterAccessJSONProperties properties = mapper.convertValue(feature.getProperties(), WaterAccessJSONProperties.class);
                Point point = (Point) reader.read(feature.getGeometry());
                MapMarkerType mapMarkerType = mapMarkerService.findTypeById((long) 2);
                MapMarker marker = new MapMarker()
                        .setType(mapMarkerType)
                        .setGeometry(point);
                Lake lake = lakeService.findByLocalId(properties.getDOWLKNUM());
                WaterAccess newWaterAccess = new WaterAccess()
                        .setAdminDescription(properties.getADM_DESCRI())
                        .setName(properties.getFAC_NAME())
                        .setDirections(properties.getDIRECTIONS())
                        .setLaunchType(properties.getLAUNCHTYPE())
                        .setRampType(properties.getRAMPTYPE())
                        .setNumberOfRamps(properties.getNUMRAMPS())
                        .setNumberOfCars(properties.getNUMCARS())
                        .setNumberOfCarTrailers(properties.getNUMCARTRLR())
                        .setNumberOfDocks(properties.getNUMDOCKS())
                        .setNumberOfADA(properties.getNUMADA())
                        .setNumberOfToilets(properties.getNUMTOILETS())
                        .setComments(properties.getCOMMENTS())
                        .setMarker(marker)
                        .setLake(lake);
            });
        }


    private FeatureCollection importFeatureCollection(String pathName) throws IOException {
            Path path = Paths.get(pathName);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(path.toFile(), FeatureCollection.class);
        }

}
