package com.flexone.catchwiseserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Entity
@Data
@Table(name ="water_accesses")
@NoArgsConstructor
@Accessors(chain = true)
public class WaterAccess {

    @Id
    private String uniqueKey;
    private String name;
    @Column(name = "administrative_description")
    private String adminDescription;
    private String directions;
    private String launchType;
    private String rampType;
    @Column(name = "number_of_ramps")
    private int numberOfRamps;
    @Column(name = "number_of_docks")
    private int numberOfDocks;
    @Column(name = "number_of_cars")
    private int numberOfCars;
    @Column(name = "number_of_car_trailers")
    private int numberOfCarTrailers;
    @Column(name = "number_of_ada")
    private int numberOfADA;
    @Column(name = "number_of_toilets")
    private int numberOfToilets;
    private String comments;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lake_id", nullable = false)
    private Lake lake;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.ALL})
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

}
