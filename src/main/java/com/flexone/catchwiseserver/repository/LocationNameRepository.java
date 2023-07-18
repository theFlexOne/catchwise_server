package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.LocationName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationNameRepository extends JpaRepository<LocationName, Long> {

    @Query(value = "select id, name, county, state from lakes_names " +
            "where ST_DWithin(marker, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), :radius) " +
            "order by ST_Distance(marker, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326));",
            nativeQuery = true)
    List<LocationName> findAllLakeNamesInRadius(Integer lng, Integer lat, Double radius);

    @Query(value = "select id, name, county, state, location_type from location_names " +
            "where st_dwithin(marker, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), :radius) " +
            "order by ST_Distance(marker, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326));",
            nativeQuery = true)
    List<LocationName> findAllLocationNamesInRadius(Double lng, Double lat, Double radius);

    @Query(value = "select l.name as lake_name, c.name as county_name, s.name as state_name from lakes l " +
            "left join counties c on l.county_id = c.id " +
            "left join states s on c.state_id = s.id " +
            "left join map_markers m on l.map_marker_id = m.id " +
            "where l.name like '%:name%' " +
            "and ST_DWithin(m.geom, ST_GeomFromText('POINT(:lng :lat)', 4326), :radius) " +
            "order by levenshtein(l.name, 'C') asc, ST_Distance(m.geom, ST_GeomFromText('POINT(:lng :lat)', 4326)) asc;",
            nativeQuery = true)
    List<LocationName> searchLocationNames(String name, Double lng, Double lat, Double radius);



}
