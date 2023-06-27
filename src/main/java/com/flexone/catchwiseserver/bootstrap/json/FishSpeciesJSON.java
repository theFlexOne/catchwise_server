package com.flexone.catchwiseserver.bootstrap.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FishSpeciesJSON {

    private String name;

    private String genus;

    private String species;

    private String description;

    @JsonProperty("fishImgUrl")
    private String imgUrl;

    private Set<String> regions;

    private Set<String> waterbodies;

    private String locationMapImgUrl;

    private List<Link> habitats;

    private List<Link> techniques;

    private List<Link> bait;

    @JsonProperty("mnLakesLocalIds")
    private List<String> localIds;

    @Data
    private static class Link {
        private String name;
        private String url;
    }
}
