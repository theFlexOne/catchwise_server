package com.flexone.catchwiseserver.bootstrap;


public class SeedDataCopy {
//
//    private final StateService stateService;
//    private final LakeService lakeService;
//    private final FishSpeciesService fishSpeciesService;
//    private final GeoJSONReader reader = new GeoJSONReader();
//
//    @Override
//    public void run(String... args) throws IOException {
//        log.info("Loading seed data...");
////        try {
////            seedStatesAndCounties(
////                    "src/main/resources/data/US_States.geojson",
////                    "src/main/resources/data/US_Counties.geojson"
////            );
//            seedNewLakes();
////            seedLakes("src/main/resources/data/MN_Lakes_Local_Data.json");
////            List<FishSpeciesJSON> fishSpeciesList = seedGameFishSpecies("src/main/resources/data/Game_Fish_Species_Data_2.json");
////        } catch (IOException e) {
////            e.printStackTrace();
////            log.info("**SEEDING FAILED**");
////            return;
////        }
//        log.info("Finished loading seed data.");
//    }
//
//    private void seedNewLakes() {
//        String pathName = "src/main/resources/data/MN_Lakes_Master.json";
//        CollectionType collectionType = new ObjectMapper()
//                .getTypeFactory().constructCollectionType(List.class, NewLakeJSON.class);
//        try {
//            List<NewLakeJSON> newLakeJSONList = new ObjectMapper().readValue(Paths.get(pathName).toFile(), collectionType);
//            List<Lake> lakeList = newLakeJSONList.stream()
//                    .map(lakeJSON -> mapNewLakeJSONToLake(lakeJSON))
//                    .toList();
//            lakeService.saveAll(lakeList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void seedStatesAndCounties(String statesPath, String countiesPath) throws IOException {
//        log.info("Loading states data...");
//        FeatureCollection statesFeatureCollection = importFeatureCollection(statesPath);
//        log.info("Loading counties data...");
//        FeatureCollection countyFeatureCollection = importFeatureCollection(countiesPath);
//
//        Feature[] features = statesFeatureCollection.getFeatures();
//        List<State> statesList = new ArrayList<>();
//        for (Feature feature : features) {
//            Map<String, Object> properties = feature.getProperties();
//            Geometry stateGeometry = reader.read(feature.getGeometry());
//            List<County> countyList = new ArrayList<>();
//            for (Feature countyFeature : countyFeatureCollection.getFeatures()) {
//
//                Map<String, Object> countyProperties = countyFeature.getProperties();
//                Geometry countyGeometry = reader.read(countyFeature.getGeometry());
//
//                if (feature.getProperties().get("STATEFP").equals(countyProperties.get("STATEFP"))) {
//                    County newCounty = new County()
//                            .setName((String) countyProperties.get("NAME"))
//                            .setFipsCode((String) countyProperties.get("COUNTYFP"))
//                            .setAnsiCode((String) countyProperties.get("COUNTYNS"))
//                            .setGeometry((MultiPolygon) countyGeometry);
//
//                    countyList.add(newCounty);
//                }
//            }
//            State newState = new State()
//                    .setName((String) properties.get("NAME"))
//                    .setAbbreviation((String) properties.get("STUSPS"))
//                    .setFipsCode((String) properties.get("STATEFP"))
//                    .setAnsiCode((String) properties.get("STATENS"))
//                    .setGeometry((MultiPolygon) stateGeometry)
//                    .addCounties(countyList);
//
//            statesList.add(newState);
//        }
//        log.info("Saving States data...");
//        stateService.saveAll(statesList);
//    }
//
////    private void seedLakes(String pathName) throws IOException {
////        CollectionType collectionType = new ObjectMapper()
////                .getTypeFactory().constructCollectionType(List.class, LakeJSON.class);
////        log.info("Loading Lakes data...");
////        List<LakeJSON> lakeJSONList = new ObjectMapper().readValue(Paths.get(pathName).toFile(), collectionType);
////        List<Lake> lakeList = lakeJSONList.stream().map(json -> mapLakeJSONToLake(json)).toList();
////
////        log.info("Saving Lakes data...");
////        lakeService.saveAll(lakeList);
////
////    }
//
//    private void seedFishSpecies(String pathName) throws IOException {
//        CollectionType collectionType = new ObjectMapper()
//                .getTypeFactory().constructCollectionType(List.class, FishSpeciesJSON.class);
//        List<FishSpeciesJSON> fishSpeciesJSONList = new ObjectMapper().readValue(Paths.get(pathName).toFile(), collectionType);
//        List<FishSpecies> fishSpeciesList = fishSpeciesJSONList.stream().map(json -> {
//            String[] scientificName = json.getSpecies().split(" ");
//            String genus = scientificName[0];
//            String species = Arrays.copyOfRange(scientificName, 1, scientificName.length).toString();
//            return new FishSpecies()
//                    .setName(json.getName())
//                    .setGenus(genus)
//                    .setSpecies(species)
//                    .setDescription(json.getDescription());
//        }).toList();
//        fishSpeciesService.saveAll(fishSpeciesList);
//    }
//
//    private List<FishSpeciesJSON> seedGameFishSpecies(String pathName) throws IOException {
//        CollectionType collectionType = new ObjectMapper()
//                .getTypeFactory().constructCollectionType(List.class, FishSpeciesJSON.class);
//        List<FishSpeciesJSON> gameFishSpeciesJSONList = new ObjectMapper().readValue(Paths.get(pathName).toFile(), collectionType);
//        List<FishSpecies> fishSpeciesList = gameFishSpeciesJSONList.stream().map(json -> {
//            if (json == null) {
//                log.info("Null json for fish species {} {}.", json.getGenus(), json.getSpecies());
//                return null;
//            }
//            String[] scientificName = json.getSpecies().split(" ");
//            String genus = scientificName[0];
//            String species = Arrays.copyOfRange(scientificName, 1, scientificName.length).toString();
//            return new FishSpecies()
//                    .setName(json.getName())
//                    .setGenus(genus)
//                    .setSpecies(species)
//                    .setDescription(json.getDescription());
//        }).toList();
//        fishSpeciesService.saveAll(fishSpeciesList);
//        return gameFishSpeciesJSONList;
//    }
//
////    private Lake mapLakeJSONToLake(LakeJSON json) {
////        return new Lake()
////                .setName(json.getName())
////                .setLocalId(json.getLocalId())
////                .setCountyFips(json.getCountyFips())
////                .setNearestTown(json.getNearestTown())
////                .setGeometry(new Point(new Coordinate(json.getCoordinates().getLng(), json.getCoordinates().getLat()), new PrecisionModel(), 4326));
////    }
//
//    private Lake mapNewLakeJSONToLake(NewLakeJSON json) {
//        Polygon geometry = new GeometryFactory().createPolygon(new CoordinateArraySequence((CoordinateSequence) json.getCoordinates()));
//        return new Lake()
//                .setName(json.getName())
//                .setLocalId(json.getLocalId())
//                .setNearestTown(json.getNearestTown())
//                .setGeometry(geometry)
//                .setFishSpecies(json.getFish().stream().map(
//                        fishSpeciesJSON -> {
//                            String[] scientificName = fishSpeciesJSON.getSpecies().split(" ");
//                            String genus = scientificName[0];
//                            String species = Arrays.copyOfRange(scientificName, 1, scientificName.length).toString();
//                            FishSpecies fishSpecies = fishSpeciesService.findByGenusAndSpecies(genus, species);
//
//    private FeatureCollection importFeatureCollection(String pathName) throws IOException {
//        return new ObjectMapper().readValue(Paths.get(pathName).toFile(), FeatureCollection.class);
//    }
//

}
