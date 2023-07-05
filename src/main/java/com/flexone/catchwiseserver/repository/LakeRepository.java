package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.domain.LakeMarker;
import com.flexone.catchwiseserver.dto.LakeNameDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LakeRepository extends JpaRepository<Lake, Long> {


    Lake findByLocalId(String localId);

    List<Lake> findByLocalIdIn(List<String> localIds);

    @Query(value = "SELECT l.* FROM lakes AS l WHERE ST_Within(l.geom, (SELECT s.geom FROM states AS s WHERE s.name = :state))", nativeQuery = true)
    List<Lake> findAllLakesInStateByStateName(String state);

    @Query(
            value = "SELECT l.* FROM public.lakes AS l " +
                    "INNER JOIN public.lakes_fish_species as lfs ON l.id = lfs.lake_id " +
                    "INNER JOIN public.fish_species AS fs ON fs.id = lfs.fish_species_id " +
                    "WHERE fs.name = :fish " +
                    "AND ST_DWithin(l.geom\\:\\:geography, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)\\:\\:geography, :miles) " +
                    "ORDER BY ST_Distance(l.geom\\:\\:geography, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)\\:\\:geography);",
            nativeQuery = true
    )
    List<Lake> findLakesInRangeByFish(Double lng, Double lat, Long miles, String fish);


    @Query(value =
            "SELECT lm.*, " +
                    "F1_Meters_To_Miles(St_Distance(lm.geom, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326))) AS distance_in_miles " +
                    "FROM lake_markers lm " +
                    "WHERE ST_DWithin(lm.geom\\:\\:geography, ST_SetSRID(ST_MakePoint(:lng, :lat)\\:\\:geography, 4326), :meters) " +
                    "AND lm.lake_name IS NOT NULL " +
                    "ORDER BY distance_in_miles"
            , nativeQuery = true)
    List<LakeMarker> findAllLakeMarkersInRange(Double lng, Double lat, Long meters);

    @Query(value = FIND_ALL_LAKE_NAMES, nativeQuery = true)
    List<LakeNameProjection> findAllLakeNames(Double lng, Double lat);

    // region QUERIES
    String FIND_ALL_LAKE_NAMES = "select l.id, l.name, c.name as county, s.name as state " +
            "from lakes l " +
            "join counties c on c.id = l.county_id " +
            "join states s on s.id = c.state_id " +
            "where l.geom && ST_Expand(st_setsrid(st_makepoint(:lng, :lat), 4326), 100000) " +
            "and l.name is not null;";
    // endregion
}


/**
 * 1. Find all lakes within a given bounding box with a given buffer distance
 */