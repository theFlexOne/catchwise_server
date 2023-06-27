package com.flexone.catchwiseserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties({"countyFips", "localId", "nearestTown", "fishUrl"})
public class LakeShortDTO extends LakeDTO {

}
