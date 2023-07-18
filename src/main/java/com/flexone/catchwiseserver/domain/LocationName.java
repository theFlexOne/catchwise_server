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
    @Column(name = "name")
    String name;
    @Column(name = "county")
    String county;
    @Column(name = "state")
    String state;
    @Column(name = "location_type")
    String type;
}
