package com.flexone.catchwiseserver.service;

import com.flexone.catchwiseserver.domain.WaterAccess;
import com.flexone.catchwiseserver.repository.WaterAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaterAccessService {

    final WaterAccessRepository waterAccessRepository;

    public WaterAccess save(WaterAccess waterAccess) {
        return waterAccessRepository.save(waterAccess);
    }

}
