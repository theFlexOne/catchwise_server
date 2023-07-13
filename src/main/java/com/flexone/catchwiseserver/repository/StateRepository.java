package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findByFipsCode(String fips);

    Optional<State> findByAbbreviation(String abbr);
}
