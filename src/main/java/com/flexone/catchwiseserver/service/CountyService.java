package com.flexone.catchwiseserver.service;

import com.flexone.catchwiseserver.domain.County;
import com.flexone.catchwiseserver.repository.CountyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountyService {

  final CountyRepository countyRepository;


  public Optional<County> findByFipsCode(String fipsCode) {
    return countyRepository.findByFips(fipsCode);
  }


  public void save(County county) {
    countyRepository.save(county);
  }
  public void saveAll(List<County> counties) {
    countyRepository.saveAll(counties);
  }

  public County findByName(String county) {
    return countyRepository.findByName(county).orElseThrow();
  }

  public County findByNameAndStateName(String county, String state) {
    County foundCounty = countyRepository.findByNameAndStateName(county, state).orElse(null);
    if (foundCounty == null) {
      log.error("County not found: " + county + ", " + state);
    }
    return foundCounty;
  }

  public County findById(Long countyId) {
    return countyRepository.findById(countyId).orElseThrow();
  }

  public County findByFips(String countyFips) {
    countyFips = padLeft(countyFips, '0', 3);
    Optional<County> county = countyRepository.findByFips(countyFips);
    if (county.isEmpty()) {
      log.info("County not found: " + countyFips);
    }
    return county.orElse(null);
  }

  public County findByCountyNameAndStateName(String countyName, String stateName) {
    Optional<County> county = countyRepository.findByCountyNameAndStateName(countyName, stateName);
    if (county.isEmpty()) {
      log.error("County not found: " + countyName + ", " + stateName);
    }
    return county.orElse(null);
  }

  private static String padLeft(String input, char padChar, int length) {
    String output = input;
    while (output.length() < length) {
      output = padChar + output;
    }
    return output;
  }


}
