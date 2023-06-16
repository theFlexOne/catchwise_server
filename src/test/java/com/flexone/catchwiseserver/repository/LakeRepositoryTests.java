package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.MockLakeData;
import com.flexone.catchwiseserver.domain.FishSpecies;
import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.mock.MockDataJSON;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LakeRepositoryTests {

    @Autowired
    private LakeRepository lakeRepository;

    private List<Lake> lakes;

    @BeforeEach
    public void setup() {
        lakes = MockDataJSON.build();
    }

    @AfterEach
    public void teardown() {
        lakeRepository.deleteAll();
    }


    @Test
    public void lakeRepository_saveAll_savesAllLakes() {
        List<Lake> savedLakes = lakeRepository.saveAll(lakes);

        assertNotNull(savedLakes);
        assertEquals(2, savedLakes.size());
        assertTrue(savedLakes.stream().allMatch(lake -> lake.getId() > 0));
    }

    @Test
    public void lakeRepository_findAllLakesInStateByStateName_returnsAllLakesInState() {
        List<Lake> savedLakes = lakeRepository.saveAll(lakes);

        List<Lake> lakesInState = lakeRepository.findAllLakesInStateByStateName("Colorado");

        assertNotNull(lakesInState);
        assertEquals(2, lakesInState.size());
        assertTrue(lakesInState.stream().allMatch(lake -> lake.getId() > 0));
    }

}
