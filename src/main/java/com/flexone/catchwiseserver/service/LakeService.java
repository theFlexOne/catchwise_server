package com.flexone.catchwiseserver.service;

import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.dto.FishSpeciesDTO;
import com.flexone.catchwiseserver.dto.LakeDTO;
import com.flexone.catchwiseserver.dto.LakeMarkerDTO;
import com.flexone.catchwiseserver.dto.LakeShortDTO;
import com.flexone.catchwiseserver.mapper.FishSpeciesMapper;
import com.flexone.catchwiseserver.mapper.LakeMapper;
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
    final FishSpeciesMapper fishSpeciesMapper;
//
    public List<FishSpeciesDTO> findLakeFishById(Long id) {
        log.info("Id: {}", id);
        Lake lake = lakeRepository.findById(id).orElseThrow();
        log.info("Lake: {}", lake);
        return fishSpeciesMapper.toDtoList(lake.getFishSpecies().stream().toList());
    }
//
    public LakeDTO findById (Long id) {
        Lake lake = lakeRepository.findById(id).orElseThrow();
        LakeDTO lakeDTO = lakeMapper.toDto(lake);
        log.info("Lake: {}", lake);
        log.info("LakeDTO: {}", lakeDTO);
        return lakeDTO;
    }

    public List<LakeMarkerDTO> findLakeMarkersInRange(Double lng, Double lat, Long range) {
        List<Lake> lakes = lakeRepository.findAllLakesInRange(lng, lat, range);
        log.info("lake count {}", lakes.size());
        if (lakes.size() > 0) {
            log.info("first lake {}", lakes.get(0));
        }
        return lakeMapper.toMarkerDtoList(lakes);
    }

//
//    public List<LakeShortDTO> findAllLakesInState(String stateName) {
//        List<Lake> lakes = lakeRepository.findAllLakesInStateByStateName(stateName);
//        return lakes.stream().map(this::lakeToLakeResponseShortDTO).toList();
//    }
//
//    public void save(Lake lake) {
//        lakeRepository.save(lake);
//    }
//
    public void saveAll(Iterable<Lake> lakes) {
        lakeRepository.saveAll(lakes);
    }
//
//    public List<LakeShortDTO> findLakesInRange(Double lat, Double lng, Long range) {
//        List<Lake> lakes = lakeRepository.findLakesInRangeInMeters(lat, lng, range);
//        return lakes.stream().map(this::lakeToLakeResponseShortDTO).toList();
//    }
//
//    public List<LakeShortDTO> findLakesInRangeByFish(Double lat, Double lng, Long range, String fish) {
//        List<Lake> lakes = lakeRepository.findLakesInRangeInMetersByFish(lat, lng, range, fish);
//        return lakes.stream().map(this::lakeToLakeResponseShortDTO).toList();
//    }
//
    public List<LakeDTO> findByLocalIdIn(List<String> localIds) {
        List<Lake> lakes = lakeRepository.findByLocalIdIn(localIds);
        return lakeMapper.toDtoList(lakes);
    }


}
