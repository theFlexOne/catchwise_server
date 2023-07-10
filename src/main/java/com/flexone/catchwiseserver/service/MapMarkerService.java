package com.flexone.catchwiseserver.service;

import com.flexone.catchwiseserver.domain.MapMarker;
import com.flexone.catchwiseserver.domain.MapMarkerType;
import com.flexone.catchwiseserver.repository.MapMarkerRepository;
import com.flexone.catchwiseserver.repository.MapMarkerTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapMarkerService {

    final MapMarkerRepository mapMarkerRepository;
    final MapMarkerTypeRepository mapMarkerTypeRepository;

    public MapMarker save(MapMarker mapMarker) {
        return mapMarkerRepository.save(mapMarker);
    }


    public MapMarkerType findTypeById(long id) {
        return mapMarkerTypeRepository.findById(id).orElseThrow();
    }
}
