-- =========== Table: states ===========
-- Description: Table containing information about states
-- ======================================
CREATE TABLE IF NOT EXISTs public.states (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    abbreviation VARCHAR(255),
    fips VARCHAR(255),
    ansi VARCHAR(255),
    geom public.geometry(MultiPolygon, 4326)
);

-- =========== Table: counties ===========
-- Description: Table containing information about counties
-- =======================================
CREATE TABLE IF NOT EXISTs public.counties (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    fips VARCHAR(255),
    ansi VARCHAR(255),
    geom public.geometry(MultiPolygon, 4326),
    state_id bigint NOT NULL,
    FOREIGN KEY (state_id) REFERENCES public.states(id)
);

-- =========== Table: lakes ===========
-- Description: Table containing information about lakes
-- =====================================
CREATE TABLE IF NOT EXISTs public.lakes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    county_fips VARCHAR(255),
    local_id VARCHAR(255),
    nearest_town VARCHAR(255),
    geom public.geometry(Point, 4326)
);

-- =========== Table: fish_species ===========
-- Description: Table containing information about fish species
-- ============================================
CREATE TABLE IF NOT EXISTs public.fish_species (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    genus VARCHAR(255),
    species VARCHAR(255),
    description VARCHAR(10000),
    fish_img_url VARCHAR(255),
    location_map_img_url VARCHAR(255)
);

-- =========== Table: lakes_fish_species ===========
-- Description: Table linking lakes and fish species
-- ================================================
CREATE TABLE IF NOT EXISTs public.lakes_fish_species (
    lake_id bigint NOT NULL,
    fish_species_id bigint NOT NULL,
    PRIMARY KEY (lake_id, fish_species_id),
    FOREIGN KEY (lake_id) REFERENCES public.lakes(id),
    FOREIGN KEY (fish_species_id) REFERENCES public.fish_species(id)
);

-- =========== Table: fish_species_regions ===========
-- Description: Table linking fish species and regions
-- =======================================================
CREATE TABLE IF NOT EXISTs public.fish_species_regions (
    fish_species_id bigint NOT NULL,
    region VARCHAR(255),
    FOREIGN KEY (fish_species_id) REFERENCES public.fish_species(id)
);

-- =========== Table: fish_species_waterbodies ===========
-- Description: Table linking fish species and waterbodies
-- =======================================================
CREATE TABLE IF NOT EXISTs public.fish_species_waterbodies (
    fish_species_id bigint NOT NULL,
    waterbody VARCHAR(255),
    FOREIGN KEY (fish_species_id) REFERENCES public.fish_species(id)
);