package com.flexone.catchwiseserver.service;

import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.dto.LakeDTO;
import com.flexone.catchwiseserver.dto.LakeMarkerDTO;
import com.flexone.catchwiseserver.mapper.LakeMapper;
import com.flexone.catchwiseserver.repository.LakeMarkerProjection;
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

    public LakeDTO findById (Long id) {
        Lake lake = lakeRepository.findById(id).orElseThrow();
        return lakeMapper.toDto(lake);
    }

    public List<LakeMarkerDTO> findAllLakeMarkersInRange(int lng, int lat) {
        List<LakeMarkerProjection> lakeMarkers = lakeRepository.findAllLakeMarkersInRange(lng, lat);
        return lakeMapper.toLakeMarkerDtoList(lakeMarkers);
    }

    // region SAVE METHODS
    public void save(Lake lake) {
        lakeRepository.save(lake);
    }
    public void saveAll(Iterable<Lake> lakes) {
        lakeRepository.saveAll(lakes);
    }
    // endregion
}
