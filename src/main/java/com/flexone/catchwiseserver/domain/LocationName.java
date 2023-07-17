package com.flexone.catchwiseserver.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "location_names")
public class LocationName {

    @Id
    Long id;
    @Column(name = "lake_name")
    String name;
    @Column(name = "county_name")
    String county;
    @Column(name = "state_name")
    String state;
}
