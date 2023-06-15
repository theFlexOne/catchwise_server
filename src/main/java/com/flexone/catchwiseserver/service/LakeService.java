package com.flexone.catchwiseserver.service;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.repository.LakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LakeService {

    final LakeRepository lakeRepository;

    public List<Lake> findAllLakes() {
        return lakeRepository.findAll();
    }

    public List<Lake> findAllLakesInState(String stateName) {
        return lakeRepository.findAllLakesInStateByStateName(stateName);
    }

    public void save(Lake lake) {
        lakeRepository.save(lake);
    }

    public void saveAll(Iterable<Lake> lakes) {
        lakeRepository.saveAll(lakes);
    }


    public Lake findLakeById(Long id) {
        return lakeRepository.findById(id).orElseThrow(() -> new RuntimeException("Lake not found"));
    }

    public List<Lake> findLakesInRange(Double lat, Double lng, Long range) {
        return lakeRepository.findLakesInRangeInMeters(lat, lng, range);
    }

    public List<Lake> findLakesInRangeByFish(Double lat, Double lng, Long range, String fish) {
        return lakeRepository.findLakesInRangeInMetersByFish(lat, lng, range, fish);
    }
}
