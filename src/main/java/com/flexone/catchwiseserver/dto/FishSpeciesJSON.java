package com.flexone.catchwiseserver.dto;

import lombok.Data;

@Data
public class FishSpeciesJSON {
    private String name;
    private String family;
    private String species;
    private String description;
    private String identification;
    private String[] commonNames;
}
