package com.flexone.catchwiseserver.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexone.catchwiseserver.domain.County;
import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.domain.State;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LakeRepositoryTests {

    @Autowired
    private LakeRepository lakeRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private CountyRepository countyRepository;


    @BeforeEach
    public void setup() throws IOException {
        File lakesFile = new File("src/test/resources/data/MN_Lakes_Mock.geojson");
        File statesFile = new File("src/test/resources/data/US_States_Mock.geojson");
        File countyFile = new File("src/test/resources/data/US_Counties_Mock.geojson");
        lakeRepository.saveAll(new ObjectMapper().readValue(lakesFile, new TypeReference<List<Lake>>() {}));
        List<State> states = new ObjectMapper().readValue(statesFile, new TypeReference<List<State>>() {});
        State mn = states.get(0);
        stateRepository.save(mn);
        List<County> counties = new ObjectMapper().readValue(countyFile, new TypeReference<List<County>>() {});
        counties.stream().forEach(county -> county.setState(mn));
        countyRepository.saveAll(counties);
    }

    @AfterEach
    public void teardown() {
        lakeRepository.deleteAll();
    }


    @Test
    public void lakeRepository_findAllLakesInStateByStateName_returnsAllLakesInState() {

        List<Lake> lakesInState = lakeRepository.findAllLakesInStateByStateName("Minnesota");

        assertNotNull(lakesInState);
        assertEquals(2, lakesInState.size());
        assertTrue(lakesInState.stream().allMatch(lake -> lake.getId() > 0));
    }

}
