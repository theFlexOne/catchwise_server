package com.flexone.catchwiseserver.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexone.catchwiseserver.bootstrap.json.LakesJSON.LakeJSON;
import com.flexone.catchwiseserver.domain.County;
import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.domain.MapMarker;
import com.flexone.catchwiseserver.service.CountyService;
import com.flexone.catchwiseserver.service.LakeService;
import com.flexone.catchwiseserver.service.MapMarkerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.wololo.jts2geojson.GeoJSONReader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class LakesSeed implements CommandLineRunner {

    final static Path waterAccessPath = Paths.get("src", "main", "resources", "NewAllLakes.json");
    final static ObjectMapper mapper = new ObjectMapper();
    final static GeometryFactory geometryFactory = new GeometryFactory();
    static final GeoJSONReader reader = new GeoJSONReader();

    final MapMarkerService mapMarkerService;
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
        List<LakeJSON> lakeJSONList = Arrays.asList(mapper.readValue(waterAccessPath.toFile(), LakeJSON[].class));
        log.info(lakeJSONList.get(0).toString());
        lakeJSONList.stream().forEach(lakeJSON -> {
            Lake lake = lakeService.findByLocalId(lakeJSON.getId());
            if (lake != null) return;
            MapMarker marker = new MapMarker()
                    .setGeometry(geometryFactory.createPoint(new Coordinate(lakeJSON.getLng(), lakeJSON.getLat())))
                    .setType(mapMarkerService.findTypeById(1));
            lake = lakeService.findByMapMarker(marker);
            if (lake != null) return;
            County county = countyService.findById(lakeJSON.getCountyId().longValue());
            lake = new Lake()
                    .setLocalId(lakeJSON.getId())
                    .setName(lakeJSON.getName())
                    .setMarker(marker)
                    .setCounty(county)
                    .setLocalId(lakeJSON.getId());
            lakeService.save(lake);
        });
    }

}
