package com.flexone.catchwiseserver.dto;

import com.flexone.catchwiseserver.repository.LakeNameProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Immutable;

public record LakeNameDTO (
    Long id,
    String name,
    String county,
    String state
) {
    public LakeNameDTO(LakeNameProjection lakeNameProjection) {
        this(lakeNameProjection.getId(), lakeNameProjection.getName(), lakeNameProjection.getCounty(), lakeNameProjection.getState());
    }

    public LakeNameDTO(Long id, String name, String county, String state) {
        this.id = id;
        this.name = name;
        this.county = county;
        this.state = state;
    }
}
