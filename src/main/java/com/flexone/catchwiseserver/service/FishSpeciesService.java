package com.flexone.catchwiseserver.service;

import com.flexone.catchwiseserver.domain.FishSpecies;
import com.flexone.catchwiseserver.dto.FishSpeciesDTO;
import com.flexone.catchwiseserver.repository.FishSpeciesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FishSpeciesService {

    final FishSpeciesRepository fishSpeciesRepository;
    final LakeService lakeService;

    public void save(FishSpecies fishSpecies) {
        fishSpeciesRepository.save(fishSpecies);
    }

    public void saveAll(List<FishSpecies> fishSpeciesList) {
        fishSpeciesRepository.saveAll(fishSpeciesList);
    }

    public List<FishSpeciesDTO> findLakeFishById(Long id) {
        return fishSpeciesRepository.findAllByLakeId(id).stream().map(fs -> {
            FishSpeciesDTO fishSpeciesDTO = new FishSpeciesDTO();
            fishSpeciesDTO.setId(fs.getId());
            fishSpeciesDTO.setName(fs.getName());
            return fishSpeciesDTO;
        }).toList();
    }

    public FishSpecies findByGenusAndSpecies(String genus, String species) {
        return fishSpeciesRepository.findByGenusAndSpeciesAllIgnoreCase(genus, species);
    }

    public FishSpecies findByScientificName(String scientificName) {
        String[] genusAndSpecies = scientificName.split(" ");
        return fishSpeciesRepository.findByGenusAndSpeciesAllIgnoreCase(genusAndSpecies[0], genusAndSpecies[1]);
    }

}