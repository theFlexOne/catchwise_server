package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.MapMarkerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MapMarkerTypeRepository extends JpaRepository<MapMarkerType, Long> {
    MapMarkerType save(MapMarkerType mapMarkerType);

    Optional<MapMarkerType> findById(long id);
}
