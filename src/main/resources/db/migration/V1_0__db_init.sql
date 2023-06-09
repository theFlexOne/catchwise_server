-- Drop and create the 'catchwise' database
DROP DATABASE IF EXISTS catchwise;
CREATE DATABASE catchwise;

-- Connect to the 'catchwise' database
\c catchwise

-- Enable PostGIS and PostGIS Topology extensions
CREATE EXTENSION postgis;
CREATE EXTENSION postgis_topology;

-- Create tables
CREATE TABLE IF NOT EXISTS countries (
  id serial PRIMARY KEY,
  name varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS states (
  id serial PRIMARY KEY,
  name varchar(255) NOT NULL,
  abbr varchar(2) NOT NULL
);

CREATE TABLE IF NOT EXISTS counties (
  id serial PRIMARY KEY,
  name varchar(255) NOT NULL,
  state_id int NOT NULL,
  FOREIGN KEY (state_id) REFERENCES states(id)
);

CREATE TABLE IF NOT EXISTS lakes (
  id serial PRIMARY KEY,
  name varchar(255) NOT NULL,
  county_id int NOT NULL,
  FOREIGN KEY (county_id) REFERENCES counties(id)
);

CREATE TABLE IF NOT EXISTS geo_data (
  id serial PRIMARY KEY,
  type varchar(255) NOT NULL,
  geometry GEOMETRY NOT NULL,
  properties text
);

CREATE TABLE IF NOT EXISTS geo_data_lakes (
  geo_data_id int NOT NULL,
  lake_id int NOT NULL,
  PRIMARY KEY (geo_data_id, lake_id),
  FOREIGN KEY (geo_data_id) REFERENCES geo_data(id),
  FOREIGN KEY (lake_id) REFERENCES lakes(id)
);

CREATE TABLE IF NOT EXISTS geo_data_counties (
  geo_data_id int NOT NULL,
  county_id int NOT NULL,
  PRIMARY KEY (geo_data_id, county_id),
  FOREIGN KEY (geo_data_id) REFERENCES geo_data(id),
  FOREIGN KEY (county_id) REFERENCES counties(id)
);

CREATE TABLE IF NOT EXISTS geo_data_states (
  geo_data_id int NOT NULL,
  state_id int NOT NULL,
  PRIMARY KEY (geo_data_id, state_id),
  FOREIGN KEY (geo_data_id) REFERENCES geo_data(id),
  FOREIGN KEY (state_id) REFERENCES states(id)
);

CREATE TABLE IF NOT EXISTS fish_species (
  id serial PRIMARY KEY,
  name varchar(255) NOT NULL,
  species varchar(255) NOT NULL,
  description text,
  identification text
);

CREATE TABLE IF NOT EXISTS common_fish_species_names (
  fish_species_id int NOT NULL,
  name varchar(255) NOT NULL,
  PRIMARY KEY (fish_species_id, name),
  FOREIGN KEY (fish_species_id) REFERENCES fish_species(id)
);

CREATE TABLE IF NOT EXISTS lakes_fish_species (
  fish_species_id int NOT NULL,
  lake_id int NOT NULL,
  PRIMARY KEY (fish_species_id, lake_id),
  FOREIGN KEY (fish_species_id) REFERENCES fish_species(id),
  FOREIGN KEY (lake_id) REFERENCES lakes(id)
);
