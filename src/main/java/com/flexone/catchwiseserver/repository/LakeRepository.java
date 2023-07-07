package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.Lake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LakeRepository extends JpaRepository<Lake, Long> {

    @Query(value =
            "select l.* from lakes l " +
                    "where l.name is not null " +
                    "and st_dwithin(l.marker, st_setsrid(st_makepoint(:lng, :lat), 4326), 1);",
            nativeQuery = true)
    List<Lake> findAllLakeMarkers(Integer lng, Integer lat);

    @Query(value =
            "select l.id lakeId, l.name lakeName, l.marker marker from lakes l " +
                    "where l.name is not null " +
                    "and st_dwithin(l.marker, st_setsrid(st_makepoint(:lng, :lat), 4326), 1);",
            nativeQuery = true)
    List<LakeMarkerView> findAllLakeMarkers2(Integer lng, Integer lat);

    @Query(value = "select l.id, l.name name, c.name countyName, s.name stateName from lakes l " +
            "left join counties c on c.id = l.county_id " +
            "left join states s on s.id = c.state_id " +
            "where st_dwithin(c.geom, st_setsrid(st_makePoint(:lng, :lat), 4326), 5);",
            nativeQuery = true)
    List<LakeNameView> findAllLakeNames(Integer lng, Integer lat);

}
