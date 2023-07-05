package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.LakeMarker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LakeMarkerRepository extends JpaRepository<LakeMarker, Long> {

    // region QUERIES
    String FIND_ALL_LAKE_MARKERS_BY_CELL = "SELECT l.id as lake_id, l.name as lake_name, l.marker as geom " +
            "FROM lakes l " +
            "inner join lakes_fish_species lfs on l.id = lfs.lake_id " +
            "inner join fish_species fs on fs.id = lfs.fish_species_id " +
            "WHERE l.marker && ST_MakeEnvelope(:lng - 1, :lat - 1, :lng + 2, :lat + 2, 4326) " +
            "AND l.name IS NOT null " +
            "group by l.id, l.name, l.marker;";
    // endregion

    @Query(value = FIND_ALL_LAKE_MARKERS_BY_CELL, nativeQuery = true)
    List<LakeMarker> findAllLakeMarkersByCell(Integer lng, Integer lat);

    @Query(value =
            "SELECT lm.*, " +
                    "F1_Meters_To_Miles(St_Distance(lm.geom\\:\\:geography, ST_SetSRID(ST_MakePoint(:lng, :lat)\\:\\:geography, 4326))) AS distance_in_miles " +
                    "FROM lake_markers lm " +
                    "WHERE lm.lake_name IS NOT NULL " +
                    "ORDER BY distance_in_miles"
            , nativeQuery = true)
    List<LakeMarker> findAllLakeMarkersInRange(Double lng, Double lat);

    @Query(value = "select * from f1_get_lakes_with_fish_species(:fishSpecies) " +
            "order by st_distance(geom, st_setsrid(st_makepoint(:lng, :lat), 4326));"
            , nativeQuery = true)
    List<LakeMarker> findAllLakeMarkersInRangeByFishSpecies(Double lng, Double lat, String fishSpecies);

    @Query(value =
            "SELECT lm.* " +
                    "FROM lake_markers lm " +
                    "WHERE lm.lake_id = :id "
            , nativeQuery = true)
    LakeMarker findLakeMarkerByLakeId(Number id);

    @Query(value =
            "SELECT lm.* " +
                    "FROM lake_markers lm " +
                    "WHERE lm.lake_name IS NOT NULL " +
                    "ORDER BY ST_Distance(lm.geom\\:\\:geography, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)\\:\\:geography) " +
                    "LIMIT 1"
            , nativeQuery = true)
    LakeMarker getNearestLakeMarker(Double lng, Double lat);




}
