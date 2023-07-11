
package com.flexone.catchwiseserver.bootstrap.json.LakesJSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LakeJSON {

    @JsonProperty("apr_ids")
    private List<String> aprIds;
    private Bbox bbox;
    private String border;
    private String county;
    @JsonProperty("county_id")
    private Long countyId;
    private List<String> fishSpecies;
    private String id;
    private List<Object> invasiveSpecies;
    private List<String> mapid;
    private Morphology morphology;
    private String name;
    @JsonProperty("nearest_town")
    private String nearestTown;
    private String notes;
    @JsonProperty("pca_id")
    private String pcaId;
    private LakePoint point;
    private Resources resources;
    private List<SpecialFishingReg> specialFishingRegs;

    public Double getLng() {
        return point.getEpsg4326().get(0);
    }

    public Double getLat() {
        return point.getEpsg4326().get(1);
    }

    public String toString() {
        return "LakeJSON [aprIds=" + aprIds + ", bbox=" + bbox + ", border=" + border + ", county=" + county
                + ", countyId=" + countyId + ", fishSpecies=" + fishSpecies + ", id=" + id + ", invasiveSpecies="
                + invasiveSpecies + ", mapid=" + mapid + ", morphology=" + morphology + ", name=" + name
                + ", nearestTown=" + nearestTown + ", notes=" + notes + ", pcaId=" + pcaId + ", point=" + point
                + ", resources=" + resources + ", specialFishingRegs=" + specialFishingRegs + "]";
    }
}
