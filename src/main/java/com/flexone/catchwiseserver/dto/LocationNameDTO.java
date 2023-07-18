package com.flexone.catchwiseserver.dto;

import com.flexone.catchwiseserver.domain.LocationName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class LocationNameDTO {

    Long id;
    String name;
    String type;
    String county;
    String state;


    public static LocationNameDTO from(LocationName locationName) {
        return new LocationNameDTO()
                .setId(locationName.getId())
                .setName(locationName.getName())
                .setType(locationName.getType())
                .setCounty(locationName.getCounty())
                .setState(locationName.getState());
    }
    public static List<LocationNameDTO> from(List<LocationName> locationNames) {
        return locationNames.stream().map(LocationNameDTO::from).toList();
    }
}
