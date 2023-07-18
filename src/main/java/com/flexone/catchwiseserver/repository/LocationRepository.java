package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query(value = "select * from locations " +
            "where ST_DWithin(geom, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), :radius)",
            nativeQuery = true)
    List<Location> findAllMarkersInRadius(Integer lng, Integer lat, Double radius);


}

