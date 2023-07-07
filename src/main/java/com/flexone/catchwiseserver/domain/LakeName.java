package com.flexone.catchwiseserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "lakes_names")
public class LakeName {

    @Id
    Long id;
    String name;
    String county;
    String state;
}
