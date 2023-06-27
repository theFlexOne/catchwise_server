package com.flexone.catchwiseserver.domain;

import javax.persistence.*;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashSet;
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
    @CollectionTable(name = "fish_species_regions", joinColumns = @JoinColumn(name = "fish_species_id"))
    @Column(name = "region")
    private Set<String> regions = new HashSet<>();


    @ElementCollection
    @CollectionTable(name = "fish_species_waterbodies", joinColumns = @JoinColumn(name = "fish_species_id"))
    @Column(name = "waterbody")
    private Set<String> waterbodies = new HashSet<>();

    @ManyToMany(mappedBy = "fishSpecies")
    private Set<Lake> lakes = new HashSet<>();

    public String getScientificName() {
        return this.genus + " " + this.species;
    }

}
