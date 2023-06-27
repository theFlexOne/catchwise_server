package com.flexone.catchwiseserver.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Coordinate;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class LatLng {

    private Double lat;
    private Double lng;

}
