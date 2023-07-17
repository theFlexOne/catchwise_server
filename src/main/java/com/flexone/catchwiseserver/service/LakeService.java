package com.flexone.catchwiseserver.service;

import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.domain.LocationName;
import com.flexone.catchwiseserver.domain.MapMarker;
import com.flexone.catchwiseserver.dto.*;
import com.flexone.catchwiseserver.repository.LocationNameRepository;
import com.flexone.catchwiseserver.repository.LakeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LakeService {

    final LakeRepository lakeRepository;
    final LocationNameRepository locationNameRepository;

    public LakeDTO findById(Long id) throws Exception {
        Lake lake = lakeRepository.findById(id).orElseThrow(() -> new Exception("Lake not found"));
        List<FishSpeciesDTO> fishSpeciesDTOList = lake.getFishSpecies().stream().map(fishSpecies -> {
            return new FishSpeciesDTO().setId(fishSpecies.getId())
                    .setName(fishSpecies.getName())
                    .setGenus(fishSpecies.getGenus())
                    .setSpecies(fishSpecies.getSpecies())
                    .setDescription(fishSpecies.getDescription())
                    .setImgUrl(fishSpecies.getImgUrl())
                    .setLocationMapImgUrl(fishSpecies.getLocationMapImgUrl());
        }).toList();
        return new LakeDTO()
                .setId(lake.getId())
                .setName(lake.getName())
                .setLocalId(lake.getLocalId())
                .setGeometry(lake.getGeometry())
                .setFishSpecies(fishSpeciesDTOList);
    }

    public Lake findByLocalId(String localLakeId) {
        return lakeRepository.findByLocalId(localLakeId).orElse(null);
    }


    public List<LocationNameDTO> findLakeNames(Double lng, Double lat, Double radius) {
        List<LocationName> locationNameList = locationNameRepository.findAllLakeNamesInRadius(lng.intValue(), lat.intValue(), radius);
        return locationNameList.stream().map(locationName -> {
            return new LocationNameDTO()
                    .setId(locationName.getId())
                    .setName(locationName.getName())
                    .setCounty(locationName.getCounty())
                    .setState(locationName.getState());
        }).toList();
    }

    public void save(Lake lake) {
        try {

        lakeRepository.save(lake);
        } catch (Exception e) {
            log.error("Error saving lake: " + lake.getName());
            log.error(e.getMessage());
        }
    }

    public Lake findByMapMarker(MapMarker marker) {
        return lakeRepository.findByMapMarkerCoordinates(marker.getGeometry().getX(), marker.getGeometry().getY()).orElse(null);
    }
}
