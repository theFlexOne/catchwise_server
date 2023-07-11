
package com.flexone.catchwiseserver.bootstrap.json.LakesJSON;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bbox {

    @JsonProperty("epsg:26915")
    private List<Long> epsg26915;
    @JsonProperty("epsg:4326")
    private List<Double> epsg4326;
}
