package com.flexone.catchwiseserver.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexone.catchwiseserver.service.CountyService;
import com.flexone.catchwiseserver.service.LakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.wololo.jts2geojson.GeoJSONReader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(2)
public class LakesSeed implements CommandLineRunner {

    final static Path waterAccessPath = Paths.get("src", "main", "resources", "data", "NewAllLakes.json");
    final static ObjectMapper mapper = new ObjectMapper();
    final static GeometryFactory geometryFactory = new GeometryFactory();
    static final GeoJSONReader reader = new GeoJSONReader();

    final LakeService lakeService;
    final CountyService countyService;

    @Value("${app.seed.enabled:false}")
    private boolean seedEnabled;

        @Override
        public void run(String... args) throws Exception {
            if (!seedEnabled) return;
            seed();
        }

    private void seed() throws IOException {
//        List<LakeJSON> lakeJSONList = Arrays.asList(mapper.readValue(waterAccessPath.toFile(), LakeJSON[].class));
//        log.info(lakeJSONList.get(0).toString());
//        lakeJSONList.stream().forEach(lakeJSON -> {
//            Lake lake = lakeService.findByLocalId(lakeJSON.getId());
//            if (lake != null) return;
//            Location location = new Location()
//                    .setGeometry(geometryFactory.createPoint(new Coordinate(lakeJSON.getLng(), lakeJSON.getLat())))
//                    .setType(locationService.findTypeById(1).getType());
//            lake = lakeService.findByLocation(location);
//            if (lake != null) return;
//
//            County county = countyService.findByCountyNameAndStateName(lakeJSON.getCounty(), "Minnesota");
//
//            lake = new Lake()
//                    .setLocalId(lakeJSON.getId())
//                    .setName(lakeJSON.getName())
//                    .setLocation(location)
//                    .setCounty(county)
//                    .setLocalId(lakeJSON.getId());
//            lakeService.save(lake);
//        });
    }


}
