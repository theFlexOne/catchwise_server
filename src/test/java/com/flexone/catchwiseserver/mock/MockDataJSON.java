package com.flexone.catchwiseserver.mock;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexone.catchwiseserver.domain.County;
import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.domain.State;
import com.flexone.catchwiseserver.service.StateService;
import lombok.Data;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.MultiPolygon;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MockDataJSON {

    @Autowired
    static StateService stateService;

    public static void build() throws IOException {
        File file = new File("src/test/java/com/flexone/catchwiseserver/mockData.json");
        StateJSON stateJSON = new ObjectMapper().readValue(file, StateJSON.class);
        State state = new State()
                .setFipsCode(stateJSON.getFipsCode())
                .setAnsiCode(stateJSON.getAnsiCode())
                .setName(stateJSON.getName())
                .setAbbreviation(stateJSON.getAbbreviation())
                .setGeometry(stateJSON.getGeometry())
                .setCounties(stateJSON.getCounties().stream()
                .map(countyJSON -> new County()
                        .setName(countyJSON.getName())
                        .setFipsCode(countyJSON.getFipsCode())
                        .setGeometry(countyJSON.getGeometry())
                ).toList());

        List<Lake> lakeList = stateJSON.getCounties().stream()
                .map(countyJSON -> countyJSON.getLakes().stream()
                        .map(lakeJSON -> new Lake()
                                .setName(lakeJSON.getName())
                                .setCounty(state.getCounties().stream()
                                        .filter(county -> county.getFipsCode().equals(countyJSON.getFipsCode()))
                                        .findFirst().orElseThrow())
                                .setLocalId(lakeJSON.getLocalId())
                                .setNearestTown(lakeJSON.getNearestTown())
                                .setGeometry(lakeJSON.getGeometry())
                        ).toList()
                ).flatMap(List::stream).toList();
        );
        stateService.save(state);
    }

    @Data
    @Accessors(chain = true)
    private static class StateJSON {
        private String fipsCode;
        private String ansiCode;
        private String name;
        private String abbreviation;
        private MultiPolygon geometry;
        private List<CountyJSON> counties;
    }

    @Data
    @Accessors(chain = true)
    private static class CountyJSON {
        private String name;
        private String fipsCode;
        private String ansiCode;
        private MultiPolygon geometry;
        private List<LakeJSON> lakes;
    }

    @Data
    @Accessors(chain = true)
    private static class LakeJSON {
        private String name;
        @JsonProperty("county_fips")
        private String countyFips;

        @JsonProperty("local_id")
        private String localId;

        @JsonProperty("nearest_town")
        private String nearestTown;

        private String geometry;
    }
}
