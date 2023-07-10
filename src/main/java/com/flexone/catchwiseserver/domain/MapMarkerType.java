package com.flexone.catchwiseserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "map_marker_types")
@AllArgsConstructor
@NoArgsConstructor
public class MapMarkerType {

    @Id
    Long id;

    String type;
}
