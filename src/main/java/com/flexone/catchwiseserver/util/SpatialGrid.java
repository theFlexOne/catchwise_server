package com.flexone.catchwiseserver.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SpatialGrid {
    private final double cellSize = 0.9;

    public String getCellKey(int lng, int lat) {
        String key = lng + ":" + lat;
        log.info("key: {}", key);
        return key;
    }

    public List<String> getCellKeys(int lng, int lat) {
        List<String> cellList = new ArrayList<>();
        for (int i = lng - 1; i <= lng + 1; i++) {
            for (int j = lat - 1; j <= lat + 1; j++) {
                cellList.add(lng + ":" + lat);
            }
        }
        return cellList;
    }

    private Double calculateKeyFromParallel(Double parallel) {
        int wholePart = (int) Math.floor(parallel);
        double decimalPart = parallel - wholePart;

        if ((decimalPart * 10) % 2 == 0) {
            BigDecimal bd = new BigDecimal(wholePart + decimalPart).setScale(1, RoundingMode.HALF_UP);
            return bd.doubleValue();
        } else {
            BigDecimal bd = new BigDecimal(wholePart + decimalPart + 0.1).setScale(1, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }

    }

}
