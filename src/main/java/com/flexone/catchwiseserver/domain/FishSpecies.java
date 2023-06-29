package com.flexone.catchwiseserver.domain;

import javax.persistence.*;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Accessors(chain = true)
@Table(name = "fish_species")
public class FishSpecies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
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
    @CollectionTable(name = "fish_species_common_names", joinColumns = @JoinColumn(name = "fish_species_id"))
    private List<String> commonNames;

    @ManyToMany(mappedBy = "fishSpecies")
    private Set<Lake> lakes = new HashSet<>();

    public String getScientificName() {
        return this.genus + " " + this.species;
    }
    public FishSpecies setScientificName(String scientificName) {
        String[] split = scientificName.split(" ");
        if (split.length != 2) throw new IllegalArgumentException("Scientific name must be in format: Genus Species");
        this.genus = split[0];
        this.species = split[1];
        return this;
    }

}
