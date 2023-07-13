package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CountyRepository extends JpaRepository<County, Long> {

    Optional<County> findByFips(String fipsCode);

    Optional<County> findByName(String county);

    @Query("SELECT c FROM County c WHERE c.name = :county AND c.state.name = :state")
    Optional<County> findByNameAndStateName(String county, String state);

    @Query("SELECT c FROM County c " +
            "WHERE LOWER(c.name) = LOWER(:county) AND LOWER(c.state.name) = LOWER(:state)")
    Optional<County> findByCountyNameAndStateName(String county, String state);
}
