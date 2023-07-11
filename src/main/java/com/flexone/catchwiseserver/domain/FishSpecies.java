package com.flexone.catchwiseserver.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Accessors(chain = true)
@Table(name = "fish_species")
public class FishSpecies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "genus")
    private String genus;

    @Column(name = "species")
    private String species;

    @Column(name = "description", length = 10000)
    private String description;

    @Column(name = "fish_img_url")
    private String imgUrl;

    @Column(name = "location_map_img_url")
    private String locationMapImgUrl;

    @ElementCollection
    @CollectionTable(name = "fish_species_common_names",
            joinColumns = @JoinColumn(name = "fish_species_id"))
    @Column(name = "common_name")
    private List<String> commonName;

    @ManyToMany(mappedBy = "fishSpecies")
    @JsonIgnore
    private Set<Lake> lakes = new HashSet<>();

    public FishSpecies setScientificName(String scientificName) {
        String[] split = scientificName.split(" ");
        this.genus = split[0];
        this.species = split[1];
        return this;
    }
    public String getScientificName() {
        return this.genus + " " + this.species;
    }

    @Override
    public String toString() {
        return this.getScientificName();
    }

}
