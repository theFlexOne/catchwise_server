package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.Lake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LakeRepository extends JpaRepository<Lake, Long> {

    @Query(value = "SELECT l.* FROM lakes AS l WHERE ST_Within(l.geom, (SELECT s.geom FROM states AS s WHERE s.name = :state))", nativeQuery = true)
    List<Lake> findAllLakesInStateByStateName(String state);

    @Query(
            value = "SELECT l.* FROM public.lakes AS l " +
                    "INNER JOIN public.lakes_fish_species as lfs ON l.id = lfs.lake_id " +
                    "WHERE ST_DWithin(l.geom\\:\\:geography, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)\\:\\:geography, :range) " +
                    "GROUP BY l.id " +
                    "ORDER BY ST_Distance(l.geom\\:\\:geography, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)\\:\\:geography);",
            nativeQuery = true
    )
    List<Lake> findLakesInRangeInMeters(Double lat, Double lng, Long range);

    @Query(
            value = "SELECT l.* FROM public.lakes AS l " +
                    "INNER JOIN public.lakes_fish_species as lfs ON l.id = lfs.lake_id " +
                    "INNER JOIN public.fish_species AS fs ON fs.id = lfs.fish_species_id " +
                    "WHERE fs.name = :fish " +
                    "AND ST_DWithin(l.geom\\:\\:geography, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)\\:\\:geography, :range) " +
                    "ORDER BY ST_Distance(l.geom\\:\\:geography, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)\\:\\:geography);",
            nativeQuery = true
    )
    List<Lake> findLakesInRangeInMetersByFish(Double lat, Double lng, Long range, String fish);



}


/**
 * 1. Find all lakes within a given bounding box with a given buffer distance
 */