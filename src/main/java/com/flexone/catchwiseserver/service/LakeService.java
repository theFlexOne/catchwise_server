package com.flexone.catchwiseserver.service;

import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.domain.LakeMarker;
import com.flexone.catchwiseserver.domain.LakeName;
import com.flexone.catchwiseserver.dto.*;
import com.flexone.catchwiseserver.mapper.LakeMapper;
import com.flexone.catchwiseserver.repository.LakeMarkerRepository;
import com.flexone.catchwiseserver.repository.LakeNameRepository;
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
    final LakeNameRepository lakeNameRepository;
    final LakeMarkerRepository lakeMarkerRepository;

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

//    public List<LakeMarkerDTO> findAllLakeMarkersInRange(int lng, int lat) {
//        List<LakeMarkerProjection> lakeMarkerProjections = lakeRepository.findAllLakeMarkersInRange(lng, lat);
//        log.info("Projections: " + lakeMarkerProjections);
//        return lakeMapper.toMarkerDtoList(lakeMarkerProjections);
//    }


    public List<LakeMarkerDTO> findAllLakeMarkers(Double lng, Double lat, Double radius) {
        List<LakeMarker> lakeMarkerList = lakeMarkerRepository.findAllMarkersInRadius(lng.intValue(), lat.intValue(), radius);
        return lakeMarkerList.stream().map(lakeMarker -> {
            return new LakeMarkerDTO()
                    .setId(lakeMarker.getId())
                    .setName(lakeMarker.getName())
                    .setCoordinates(lakeMarker.getCoords());
        }).toList();
    }
    public List<LakeMarkerDTO> findAllLakeMarkers(Double lng, Double lat) {
        return findAllLakeMarkers(lng, lat, 1.0);
    }


    public List<LakeNameDTO> findLakeNames(Double lng, Double lat, Double radius) {
        List<LakeName> lakeNameList = lakeNameRepository.findAllLakeNamesInRadius(lng.intValue(), lat.intValue(), radius);
        return lakeNameList.stream().map(lakeName -> {
            return new LakeNameDTO()
                    .setId(lakeName.getId())
                    .setName(lakeName.getName())
                    .setCounty(lakeName.getCounty())
                    .setState(lakeName.getState());
        }).toList();
    }
}
