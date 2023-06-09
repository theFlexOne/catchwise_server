package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.GeoLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GeoLocationRepository extends JpaRepository<GeoLocation, Long> {


    // Find all lakes within a bounding box with a buffer
    @Query("FROM Lake l WHERE ST_Contains(l.geoLocation.geometry, ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 4326)) = true")
    List<GeoLocation> findLakesWithinBoundingBox(double minLon, double minLat, double maxLon, double maxLat);

}


/*
*/