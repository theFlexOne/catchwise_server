package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.MapMarker;
import com.flexone.catchwiseserver.domain.MapMarkerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MapMarkerRepository extends JpaRepository<MapMarker, Long> {
    MapMarker save(MapMarker mapMarker);

}

