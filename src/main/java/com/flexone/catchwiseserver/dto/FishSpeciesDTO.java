package com.flexone.catchwiseserver.dto;

import lombok.Data;

@Data
public class FishSpeciesDTO {
    private Long id;
    private String name;
    private String genus;
    private String species;
    private String description;
    private String imgUrl;
    private String locationMapImgUrl;

}
