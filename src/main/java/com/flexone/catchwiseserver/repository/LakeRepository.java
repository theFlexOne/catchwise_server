package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.Lake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LakeRepository extends JpaRepository<Lake, Long> {

    @Query(value =
            "select l.* from lakes l " +
                    "where l.name is not null " +
                    "and st_dwithin(l.marker, st_setsrid(st_makepoint(:lng, :lat), 4326), 1);",
            nativeQuery = true)
    List<Lake> findAllLakeMarkers(Integer lng, Integer lat);


    Optional<Lake> findByLocalId(String localLakeId);
}
