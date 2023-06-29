package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.FishSpecies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface FishSpeciesRepository extends JpaRepository<FishSpecies, Long> {

    FishSpecies findByGenusAndSpeciesAllIgnoreCase(String species, String genus);


    @Query(value = "SELECT fs.* FROM fish_species AS fs " +
            "INNER JOIN lakes_fish_species AS lfs ON fs.id = lfs.fish_species_id " +
            "WHERE lfs.lake_id = :lakeId", nativeQuery = true)
    List<FishSpecies> findAllByLakeId(Long lakeId);


}
