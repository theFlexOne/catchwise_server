package com.flexone.catchwiseserver.service;

import com.flexone.catchwiseserver.cache.CoordKeyGenerator;
import com.flexone.catchwiseserver.domain.LakeMarker;
import com.flexone.catchwiseserver.dto.LakeMarkerDTO;
import com.flexone.catchwiseserver.mapper.LakeMarkerMapper;
import com.flexone.catchwiseserver.repository.LakeMarkerRepository;
import com.flexone.catchwiseserver.util.SpatialGrid;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LakeMarkerService {
    final static SpatialGrid spatialGrid = new SpatialGrid();
    final static GeometryFactory geometryFactory = new GeometryFactory();

    final LakeMarkerRepository lakeMarkerRepository;
    final LakeMarkerMapper lakeMarkerMapper;
    final HazelcastInstance hazelcastInstance;

    public LakeMarkerDTO findNearestLakeMarker(double lng, double lat) {
        String key = spatialGrid.getCellKey((int) lng, (int) lat);
        log.info("key: {}", key);
        return null;
    }

    public List<LakeMarkerDTO> findNearestLakeMarkers(double lng, double lat) {
        String key = String.format("%d:%d", (int) lng, (int) lat);

        IMap<String, List<LakeMarkerDTO>> lakeMarkerMap = hazelcastInstance.getMap("lakeMarkers");
        List<LakeMarkerDTO> lakeMarkerDTOList = lakeMarkerMap.get(key);
        if (lakeMarkerDTOList == null) {
            log.info("lakeMarkerList is null");
            List<LakeMarker> lakeMarkerList = lakeMarkerRepository.findAllLakeMarkersByCell((int) lng, (int) lat);
            lakeMarkerDTOList = lakeMarkerMapper.toDtoList(lakeMarkerList);
            lakeMarkerMap.put(key, lakeMarkerDTOList);
            return lakeMarkerDTOList;
        } else {
            log.info("lakeMarkerList is not null");
            return lakeMarkerDTOList;
        }


    }

    public LakeMarker findByLakeId(Number lakeId) {
        return lakeMarkerRepository.findLakeMarkerByLakeId(lakeId);
    }

    public List<LakeMarkerDTO> findAllLakeMarkersInRangeByFishSpecies(Double lng, Double lat, Integer miles, String fishSpecies) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point point = geometryFactory.createPoint(new Coordinate(lng, lat));
        List<LakeMarker> lakeMarkerList = lakeMarkerRepository.findAllLakeMarkersInRangeByFishSpecies(lng, lat, fishSpecies);
        return lakeMarkerMapper.toDtoList(lakeMarkerList.stream().filter(lm -> lm.getGeometry().distance(point) < miles * 1.5).toList());
    }

//    public List<LakeMarker> findAllLakeMarkersInRangeByFishId(Double lng, Double lat, Long range, Long fishSpeciesId) {
//        return lakeMarkerRepository.findAllLakeMarkersInRangeByFishId(lng, lat, range, fishSpeciesId);
//    }


}


