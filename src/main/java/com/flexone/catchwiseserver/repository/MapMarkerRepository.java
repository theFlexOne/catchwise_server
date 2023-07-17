package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.MapMarker;
import com.flexone.catchwiseserver.domain.MapMarkerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MapMarkerRepository extends JpaRepository<MapMarker, Long> {

    @Query(value = "select * from all_map_locations " +
            "where ST_DWithin(marker, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), :radius)" +
            "and location_name is not null", nativeQuery = true)
    List<MapMarker> findAllMarkersInRadius(Integer lng, Integer lat, Double radius);


}

