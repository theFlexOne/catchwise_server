package com.flexone.catchwiseserver.bootstrap.json.WaterAccessJSON;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
@JsonIgnoreProperties(ignoreUnknown=true)
public class WaterAccessPropertiesJSON {

    @JsonProperty("FAC_NAME")
    private String FAC_NAME;
    @JsonProperty("ADM_DESCRI")
    private String ADM_DESCRI;
    @JsonProperty("COUNTYNAME")
    private String COUNTYNAME;
    @JsonProperty("DIRECTIONS")
    private String DIRECTIONS;
    @JsonProperty("LAUNCHTYPE")
    private String LAUNCHTYPE;
    @JsonProperty("RAMPTYPE")
    private String RAMPTYPE;
    @JsonProperty("NUMRAMPS")
    private int NUMRAMPS;
    @JsonProperty("NUMDOCKS")
    private int NUMDOCKS;
    @JsonProperty("NUMCARS")
    private int NUMCARS;
    @JsonProperty("NUMCARTRLR")
    private int NUMCARTRLR;
    @JsonProperty("NUMADA")
    private int NUMADA;
    @JsonProperty("NUMTOILETS")
    private int NUMTOILETS;
    @JsonProperty("DOWLKNUM")
    private String DOWLKNUM;
    @JsonProperty("LAKE_NAME")
    private String LAKE_NAME;
    @JsonProperty("UNIQUEKEY")
    private String UNIQUEKEY;
    @JsonProperty("COMMENTS")
    private String COMMENTS;
}
