package com.flexone.catchwiseserver.service;

import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.dto.*;
import com.flexone.catchwiseserver.mapper.LakeMapper;
import com.flexone.catchwiseserver.repository.LakeMarkerView;
import com.flexone.catchwiseserver.repository.LakeNameView;
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
    final LakeMapper lakeMapper;

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


    public List<LakeMarkerDTO> findAllLakeMarkers(Double lng, Double lat) {
        List<Lake> lakeList = lakeRepository.findAllLakeMarkers(lng.intValue(), lat.intValue());
        return lakeList.stream().map(lake -> {
            return new LakeMarkerDTO()
                    .setId(lake.getId())
                    .setName(lake.getName())
                    .setCoordinates(lake.getMarker().getCoordinates());
        }).toList();
    }

    public List<LakeNameDTO> findLakeNames(Double lng, Double lat) {
        List<LakeNameView> lakeNameViews = lakeRepository.findAllLakeNames(lng.intValue(), lat.intValue());

        return lakeNameViews.stream().map(lakeNameView -> {
            return new LakeNameDTO()
                    .setId(lakeNameView.getId())
                    .setName(lakeNameView.getName())
                    .setCountyName(lakeNameView.getCountyName())
                    .setStateName(lakeNameView.getStateName());
        }).toList();

    }

    public List<LakeMarkerDTO> findAllLakeMarkers2(Double lng, Double lat) {
        List<Lake> lakeList = lakeRepository.findAllLakeMarkers(lng.intValue(), lat.intValue());
        return lakeList.stream().map(lake -> {
            return new LakeMarkerDTO()
                    .setId(lake.getId())
                    .setName(lake.getName())
                    .setCoordinates(lake.getMarker().getCoordinates());
        }).toList();
    }
}
