
package com.flexone.catchwiseserver.bootstrap.json.LakesJSON;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Morphology {

    private Double area;
    @JsonProperty("littoral_area")
    private Long littoralArea;
    @JsonProperty("max_depth")
    private Long maxDepth;
    @JsonProperty("mean_depth")
    private Long meanDepth;
    @JsonProperty("shore_length")
    private Double shoreLength;
}
