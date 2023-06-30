package com.flexone.catchwiseserver.repository;

import com.flexone.catchwiseserver.domain.FishSpecies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FishSpeciesRepository extends JpaRepository<FishSpecies, Long> {

    @Query("SELECT fs FROM FishSpecies fs WHERE LOWER(fs.genus) = LOWER(:genus) AND LOWER(fs.species) = LOWER(:species)")
    Optional<FishSpecies> findByGenusAndSpeciesAllIgnoreCase(String genus, String species);


    @Query(value = "SELECT fs.* FROM fish_species AS fs " +
            "INNER JOIN lakes_fish_species AS lfs ON fs.id = lfs.fish_species_id " +
            "WHERE lfs.lake_id = :lakeId", nativeQuery = true)
    List<FishSpecies> findAllByLakeId(Long lakeId);


    Optional<FishSpecies> findByGenusAndSpecies(String genus, String species);
}
