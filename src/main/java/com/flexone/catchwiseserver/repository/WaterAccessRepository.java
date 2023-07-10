package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.WaterAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WaterAccessRepository extends JpaRepository<WaterAccess, Long> {

    WaterAccess save(WaterAccess waterAccess);
}
