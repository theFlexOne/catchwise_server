package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.MapMarker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MapMarkerRepository extends JpaRepository<MapMarker, Long> {

    @Query(value = "SELECT * FROM lakes_map_markers " +
            "WHERE ST_DWithin(geom, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), :radius)",
            nativeQuery = true)
    CompletableFuture<List<MapMarker>> getLakesMapMarkers(Double lng, Double lat, Double radius);

    @Query(value = "SELECT * FROM water_accesses_map_markers " +
            "WHERE ST_DWithin(geom, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), :radius)",
            nativeQuery = true)
    CompletableFuture<List<MapMarker>> getWaterAccessMapMarkers(Double lng, Double lat, Double radius);

}
