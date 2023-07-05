package com.flexone.catchwiseserver.service;

import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.domain.LakeMarker;
import com.flexone.catchwiseserver.dto.LakeDTO;
import com.flexone.catchwiseserver.dto.LakeNameDTO;
import com.flexone.catchwiseserver.mapper.LakeMapper;
import com.flexone.catchwiseserver.repository.LakeNameProjection;
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

    public List<LakeMarker> findAllLakeMarkersInRange(Double lng, Double lat, Long range) {
        return lakeRepository.findAllLakeMarkersInRange(lng, lat, range);
    }

    public List<LakeDTO> findAllByLocalIdIn(List<String> localIds) {
        List<Lake> lakes = lakeRepository.findByLocalIdIn(localIds);
        return lakeMapper.toDtoList(lakes);
    }


    // region SAVE METHODS
    public void save(Lake lake) {
        lakeRepository.save(lake);
    }
    public void saveAll(Iterable<Lake> lakes) {
        lakeRepository.saveAll(lakes);
    }

    public List<LakeNameDTO> findAllLakeNames(Double lng, Double lat) {
        List<LakeNameProjection> lakeNames = lakeRepository.findAllLakeNames(lng, lat);
        return lakeMapper.toLakeNameDtoList(lakeNames);
    }
    // endregion
}
