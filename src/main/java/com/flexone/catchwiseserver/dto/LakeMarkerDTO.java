package com.flexone.catchwiseserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hazelcast.internal.serialization.SerializableByConvention;
import lombok.*;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Point;

import java.io.Serializable;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class LakeMarkerDTO implements Serializable{
    Long lakeId;
    String lakeName;
    double[] coordinates;
    double distance;

    public LakeMarkerDTO(Long lakeId, String lakeName, double[] coordinates) {
        this(lakeId, lakeName, coordinates, 0);
    }
}
