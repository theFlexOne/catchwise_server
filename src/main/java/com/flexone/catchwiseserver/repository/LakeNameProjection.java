package com.flexone.catchwiseserver.repository;

import javax.persistence.Column;

public interface LakeNameProjection {
    Long getId();
    String getName();
    String getCounty();
    String getState();
}
