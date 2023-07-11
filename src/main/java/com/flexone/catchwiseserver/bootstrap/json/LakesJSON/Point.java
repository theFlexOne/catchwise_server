
package com.flexone.catchwiseserver.bootstrap.json.LakesJSON;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Point {

    @JsonProperty("epsg:26915")
    private List<Long> epsg26915;
    @JsonProperty("epsg:4326")
    private List<Double> epsg4326;

}
