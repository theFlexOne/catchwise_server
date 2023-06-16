package com.flexone.catchwiseserver.mock;

import com.flexone.catchwiseserver.domain.County;
import com.flexone.catchwiseserver.domain.FishSpecies;
import com.flexone.catchwiseserver.domain.Lake;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MockLakeData {

    public MockLakeData() {
        File file = new File("src/test/java/com/flexone/catchwiseserver/mockData.json");
    }

    private static List<FishSpecies> buildFishSpeciesList(String... fishNames) {
        return Arrays.stream(fishNames).map(fishName -> new FishSpecies().setName(fishName)).collect(Collectors.toList());
    }


}
