package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.LakeName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LakeNameRepository extends JpaRepository<LakeName, Long> {

    @Query(value = "select id, name, county, state from lakes_names " +
            "where ST_DWithin(marker, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), :radius) " +
            "order by ST_Distance(marker, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326));",
            nativeQuery = true)
    List<LakeName> findAllLakeNamesInRadius(Integer lng, Integer lat, Double radius);
}
