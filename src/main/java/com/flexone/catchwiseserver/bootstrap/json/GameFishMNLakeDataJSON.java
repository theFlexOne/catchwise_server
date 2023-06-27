package com.flexone.catchwiseserver.bootstrap.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GameFishMNLakeDataJSON {
    private String genus;
    private String species;
    @JsonProperty("mnLakesLocalIds")
    private String[] ids;

}
