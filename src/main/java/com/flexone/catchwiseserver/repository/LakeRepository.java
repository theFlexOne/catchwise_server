package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.Lake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LakeRepository extends JpaRepository<Lake, Long> {

    // region QUERIES
    final static String FIND_ALL_LAKE_MARKERS_IN_RANGE =
            "SELECT lmv.* FROM lake_markers_view lmv " +
                    "WHERE lmv.marker && ST_MakeEnvelope(:lng - 2, :lat - 2, :lng + 3, :lat + 3, 4326);";

    // endregion

    @Query(value = FIND_ALL_LAKE_MARKERS_IN_RANGE, nativeQuery = true)
    List<LakeMarkerProjection> findAllLakeMarkersInRange(int lng, int lat);

}