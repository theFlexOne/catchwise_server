package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.Lake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LakeRepository extends JpaRepository<Lake, Long> {


    Lake findByLocalId(String localId);

    List<Lake> findByLocalIdIn(List<String> localIds);

    @Query(value = "SELECT l.* FROM lakes AS l WHERE ST_Within(l.geom, (SELECT s.geom FROM states AS s WHERE s.name = :state))", nativeQuery = true)
    List<Lake> findAllLakesInStateByStateName(String state);

    @Query(
            value = "SELECT l.*, F1_Meters_To_Miles(St_Distance(l.geom\\:\\:geography, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)\\:\\:geography)) as distance_in_miles " +
                    "from lakes as l " +
                    "WHERE ST_DWithin(ST_Centroid(l.geom)\\:\\:geography, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)\\:\\:geography, :meters) " +
                    "AND l.name IS NOT NULL " +
                    "ORDER BY distance_in_miles;",
            nativeQuery = true
    )
    List<Lake> findAllLakesInRange(Double lng, Double lat, Long meters);

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


}


/**
 * 1. Find all lakes within a given bounding box with a given buffer distance
 */