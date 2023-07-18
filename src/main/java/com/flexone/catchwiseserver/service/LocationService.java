package com.flexone.catchwiseserver.service;

import com.flexone.catchwiseserver.domain.LocationName;
import com.flexone.catchwiseserver.domain.Location;
import com.flexone.catchwiseserver.dto.LocationNameDTO;
import com.flexone.catchwiseserver.dto.LocationDTO;
import com.flexone.catchwiseserver.repository.LocationNameRepository;
import com.flexone.catchwiseserver.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {

    private final static Double DEFAULT_RADIUS = 5.0;

    private final LocationRepository locationRepository;
    private final LocationNameRepository locationNameRepository;

    public List<LocationDTO> findAllLocations(Double lng, Double lat, Double radius) {
        radius = radius == null ? DEFAULT_RADIUS : radius;
        List<Location> locations = locationRepository.findAllMarkersInRadius(lng.intValue(), lat.intValue(), radius);
        return LocationDTO.from(locations);
    }

    public List<LocationName> searchLocationNames(String query, Double lng, Double lat, Double radius) {
        return locationNameRepository.searchLocationNames(query, lng, lat, radius);
    }

    public List<LocationNameDTO> getAllLocationNames(Double lng, Double lat, Double radius) {
        List<LocationName> locationNames = locationNameRepository.findAllLocationNamesInRadius(lng, lat, radius);
        return LocationNameDTO.from(locationNames);
    }
}
