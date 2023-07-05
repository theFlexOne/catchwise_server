package com.flexone.catchwiseserver.bootstrap;


import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.flexone.catchwiseserver.bootstrap.json.FishSpeciesJSON;
import com.flexone.catchwiseserver.bootstrap.json.LakeJSON;
import com.flexone.catchwiseserver.domain.County;
import com.flexone.catchwiseserver.domain.FishSpecies;
import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.domain.State;
import com.flexone.catchwiseserver.service.CountyService;
import com.flexone.catchwiseserver.service.FishSpeciesService;
import com.flexone.catchwiseserver.service.LakeService;
import com.flexone.catchwiseserver.service.StateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.jts2geojson.GeoJSONReader;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedData implements CommandLineRunner {
//
    final LakeService lakeService;
    final StateService stateService;
    final CountyService countyService;
    final FishSpeciesService fishSpeciesService;

    static final ObjectMapper mapper = new ObjectMapper();
    static final GeoJSONReader reader = new GeoJSONReader();
    static final GeometryFactory geometryFactory = new GeometryFactory();
//
    @Override
    public void run(String... args) throws Exception {
        log.info("Nothing to seed yet");

//        seedData();
    }

    private void seedData() throws IOException {
        log.info("Seeding data");

        log.info("Seeding states");
        seedStates();
        log.info("Seeding counties");
        seedCounties();
        log.info("Seeding fish species");
        seedFishSpecies();
        log.info("Seeding lakes");
        seedLakes();

        log.info("Finished seeding data");
    }

    private void seedStates() throws IOException {
        final String pathName = "src/main/resources/data/US_States.geojson";

        FeatureCollection statesFeatureCollection = importFeatureCollection(pathName);
        Feature[] features = statesFeatureCollection.getFeatures();
        List<State> statesList = new ArrayList<>();
        for (Feature feature : features) {
            Map<String, Object> properties = feature.getProperties();
            Geometry stateGeometry = reader.read(feature.getGeometry());
            State state = new State()
                    .setName((String) properties.get("NAME"))
                    .setAbbreviation((String) properties.get("STUSPS"))
                    .setAnsiCode((String) properties.get("ANSI"))
                    .setFipsCode((String) properties.get("STATEFP"))
                    .setGeometry((MultiPolygon) stateGeometry);
            statesList.add(state);
        }
        stateService.saveAll(statesList);
    }

    private void seedCounties() throws IOException {
        final String pathname = "src/main/resources/data/US_Counties.geojson";

        FeatureCollection countiesFeatureCollection = importFeatureCollection(pathname);
        Feature[] features = countiesFeatureCollection.getFeatures();


        List<County> countiesList = new ArrayList<>();
        for (Feature feature : features) {
            State state = stateService.findByAbbreviation(feature.getProperties().get("STUSPS").toString());
            if (state == null) {
                log.error("State not found for abbreviation: {}", feature.getProperties().get("STUSPS"));
                continue;
            }
            Map<String, Object> properties = feature.getProperties();
            Geometry countyGeometry = reader.read(feature.getGeometry());
            County county = new County()
                    .setName((String) properties.get("NAME"))
                    .setAnsiCode((String) properties.get("ANSI"))
                    .setFipsCode((String) properties.get("COUNTYFP"))
                    .setGeometry((MultiPolygon) countyGeometry)
                    .setState(state);
            countiesList.add(county);
        }
        log.info("Counties list length: {}", countiesList.size());
        countyService.saveAll(countiesList);
    }

    private void seedFishSpecies() throws IOException {
        final String pathName = "src/main/resources/data/Fish_Species.json";

        CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(Collection.class, FishSpeciesJSON.class);
        List<FishSpeciesJSON> fishSpeciesJSONList = mapper.readValue(Paths.get(pathName).toFile(), collectionType);

        List<FishSpecies> fishSpeciesList = fishSpeciesJSONList
                .stream().map(json -> {
                    FishSpecies fishSpecies = fishSpeciesService.findByScientificName(json.getScientificName());
                    if (fishSpecies == null) {
                        fishSpecies = new FishSpecies()
                                .setName(json.getName())
                                .setScientificName(json.getScientificName())
                                .setCommonNames(json.getCommonNames());
                    }
                    return fishSpecies;
                }).toList();
        log.info("FishSpecies list length: {}", fishSpeciesList.size());
        fishSpeciesService.saveAll(fishSpeciesList);
    }

    private void seedLakes() throws IOException {
        final String pathName = "src/main/resources/data/MN_Lakes_Master_List.json";

        CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(Collection.class, LakeJSON.class);
        List<LakeJSON> lakeJSONList = mapper.readValue(Paths.get(pathName).toFile(), collectionType);
        List<Lake> lakesList = lakeJSONList.stream().map(json -> {
            List<FishSpecies> fishSpeciesList = json.getFishSpecies().stream().reduce(new ArrayList<>(), (list, fs) -> {
                String genus = fs.getScientificName().split(" ")[0];
                String species = fs.getScientificName().split(" ")[1];
                FishSpecies fishSpecies = fishSpeciesService.findByGenusAndSpecies(genus, species);
                if (fishSpecies != null) {
                    list.add(fishSpecies);
                }
                return list;
            }, (list1, list2) -> {
                list1.addAll(list2);
                return list1;
            });
            Geometry lakeGeometry = reader.read(json.getFeature().getGeometry());

            return new Lake()
                    .setName(json.getName())
                    .setLocalId(json.getId())
                    .setFishSpecies(fishSpeciesList)
                    .setGeometry((MultiPolygon) lakeGeometry);
        }).toList();

        lakeService.saveAll(lakesList);
    }

//    private FishSpecies mapFishSpeciesJSONToFishSpecies(FishSpeciesJSON json, List<Lake> lakesList) {
//        return new FishSpecies()
//                .setName(json.getName())
//                .setGenus(json.getGenus())
//                .setSpecies(json.getSpecies())
//                .setDescription(json.getDescription())
//                .setImgUrl(json.getImgUrl())
//                .setRegions(json.getRegions())
//                .setWaterbodies(json.getWaterbodies())
//                .setLocationMapImgUrl(json.getLocationMapImgUrl())
//                .setLakes(new HashSet<>(lakesList));
//    }
//
//    private FishSpecies mapFishSpeciesJSONToFishSpecies(FishSpeciesJSON json) {
//        return new FishSpecies()
//                .setName(json.getName())
//                .setGenus(json.getGenus())
//                .setSpecies(json.getSpecies())
//                .setDescription(json.getDescription())
//                .setImgUrl(json.getImgUrl())
//                .setRegions(json.getRegions())
//                .setWaterbodies(json.getWaterbodies())
//                .setLocationMapImgUrl(json.getLocationMapImgUrl());
//    }

    private FeatureCollection importFeatureCollection(String pathName) throws IOException {
        return new ObjectMapper().readValue(Paths.get(pathName).toFile(), FeatureCollection.class);
    }

}