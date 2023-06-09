package com.flexone.catchwiseserver.service;

import com.flexone.catchwiseserver.domain.Lake;
import com.flexone.catchwiseserver.repository.LakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LakeService {

  final LakeRepository lakeRepository;

  public void save(Lake lake) {
    lakeRepository.save(lake);
  }
  public void saveAll(Iterable<Lake> lakes) {
    lakeRepository.saveAll(lakes);
  }

}
