package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.LakeMarker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LakeMarkerRepository extends JpaRepository<LakeMarker, Long> {

    @Query(value = "select * from all_markers " +
            "where ST_DWithin(marker, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), :radius)" +
            "and name is not null", nativeQuery = true)
    List<LakeMarker> findAllMarkersInRadius(Integer lng, Integer lat, Double radius);
}
