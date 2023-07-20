package com.flexone.catchwiseserver.service;

import com.flexone.catchwiseserver.domain.MapMarker;
import com.flexone.catchwiseserver.dto.MapMarkerDTO;
import com.flexone.catchwiseserver.repository.MapMarkerRepository;
import com.flexone.catchwiseserver.util.MapMarkerView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MapMarkerService {

    final private MapMarkerRepository mapMarkerRepository;


    public List<MapMarkerDTO> getMarkers(Double lng, Double lat, Double radius, List<String> fieldsList) {
        List<MapMarker> markers = new ArrayList<>();
        try{

        if (fieldsList.contains("all")) {
            markers.addAll(mapMarkerRepository.getMapMarkers("lakes", lng, lat, radius).get());
            markers.addAll(mapMarkerRepository.getMapMarkers("water_accesses", lng, lat, radius).get());
        } else {
            markers.addAll(fieldsList.stream().reduce(new ArrayList<>(), (acc, field) -> {
                try {
                    switch (field) {
                        case "lake":
                            acc.addAll(mapMarkerRepository.getMapMarkers("lakes", lng, lat, radius).get());
                            break;
                        case "water_access":
                            acc.addAll(mapMarkerRepository.getMapMarkers("water_accesses", lng, lat, radius).get());
                            break;
                    }
                    return acc;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, (acc1, acc2) -> {
                acc1.addAll(acc2);
                return acc1;
            }));
            if (fieldsList.contains("lake")) {
                markers.addAll(mapMarkerRepository.getMapMarkers("lakes", lng, lat, radius).get());
            }
            if (fieldsList.contains("water_access")) {
                markers.addAll(mapMarkerRepository.getMapMarkers("water_accesses", lng, lat, radius).get());
            }
        }
        } catch (Exception e) {
            System.out.println(e);
        }
        return MapMarkerDTO.mapToDTO(markers);
    }
}
