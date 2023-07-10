package com.flexone.catchwiseserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Entity
@Data
@Table(name ="lakes_water_access")
@NoArgsConstructor
@Accessors(chain = true)
public class WaterAccess {

    @Id
    private String uniqueKey;
    private String name;
    @Column(name = "administrative_description", length = 100)
    private String adminDescription;
    private String directions;
    private String launchType;
    private String rampType;
    private int numberOfRamps;
    private int numberOfDocks;
    private int numberOfCars;
    private int numberOfCarTrailers;
    private int numberOfADA;
    private int numberOfToilets;
    private String comments;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "lake_id", nullable = false)
    private Lake lake;

    @OneToOne(cascade = CascadeType.ALL)
    private MapMarker marker;

    public Point getMarker() {
        return marker.getGeometry();
    }


}
