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
public class LakeMarkerDTO implements Serializable{
    Long lakeId;
    String lakeName;
    String countyName;
    String stateName;
    double[] coordinates;
    double distance;

}
