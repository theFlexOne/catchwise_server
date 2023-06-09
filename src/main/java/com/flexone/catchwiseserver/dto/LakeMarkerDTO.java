package com.flexone.catchwiseserver.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flexone.catchwiseserver.serializer.GeometrySerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Coordinates;
import org.locationtech.jts.geom.Point;

import javax.persistence.Column;

@Data
@Accessors(chain = true)
public class LakeMarkerDTO {

    Long id;

    String name;

    Double[] coordinates = new Double[2];

}
