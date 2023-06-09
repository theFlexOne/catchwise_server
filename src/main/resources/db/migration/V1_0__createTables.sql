SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;


CREATE SCHEMA IF NOT EXISTS tiger;
ALTER SCHEMA tiger OWNER TO postgres;


CREATE SCHEMA IF NOT EXISTS tiger_data;
ALTER SCHEMA tiger_data OWNER TO postgres;


CREATE SCHEMA IF NOT EXISTS topology;
ALTER SCHEMA topology OWNER TO postgres;
COMMENT ON SCHEMA topology IS 'PostGIS Topology schema';



CREATE EXTENSION IF NOT EXISTS address_standardizer WITH SCHEMA public;
COMMENT ON EXTENSION address_standardizer IS 'Used to parse an address into constituent elements. Generally used to support geocoding address normalization step.';

CREATE EXTENSION IF NOT EXISTS fuzzystrmatch WITH SCHEMA public;
COMMENT ON EXTENSION fuzzystrmatch IS 'determine similarities and distance between strings';

CREATE EXTENSION IF NOT EXISTS h3 WITH SCHEMA public;
COMMENT ON EXTENSION h3 IS 'H3 bindings for PostgreSQL';

CREATE EXTENSION IF NOT EXISTS postgis WITH SCHEMA public;
COMMENT ON EXTENSION postgis IS 'PostGIS geometry and geography spatial types and functions';

CREATE EXTENSION IF NOT EXISTS h3_postgis WITH SCHEMA public;
COMMENT ON EXTENSION h3_postgis IS 'H3 PostGIS integration';

CREATE EXTENSION IF NOT EXISTS ogr_fdw WITH SCHEMA public;
COMMENT ON EXTENSION ogr_fdw IS 'foreign-data wrapper for GIS data access';

CREATE EXTENSION IF NOT EXISTS pgrouting WITH SCHEMA public;
COMMENT ON EXTENSION pgrouting IS 'pgRouting Extension';

CREATE EXTENSION IF NOT EXISTS pointcloud WITH SCHEMA public;
COMMENT ON EXTENSION pointcloud IS 'data type for lidar point clouds';

CREATE EXTENSION IF NOT EXISTS pointcloud_postgis WITH SCHEMA public;
COMMENT ON EXTENSION pointcloud_postgis IS 'integration for pointcloud LIDAR data and PostGIS geometry data';

CREATE EXTENSION IF NOT EXISTS postgis_raster WITH SCHEMA public;
COMMENT ON EXTENSION postgis_raster IS 'PostGIS raster types and functions';

CREATE EXTENSION IF NOT EXISTS postgis_sfcgal WITH SCHEMA public;
COMMENT ON EXTENSION postgis_sfcgal IS 'PostGIS SFCGAL functions';

CREATE EXTENSION IF NOT EXISTS postgis_tiger_geocoder WITH SCHEMA tiger;
COMMENT ON EXTENSION postgis_tiger_geocoder IS 'PostGIS tiger geocoder and reverse geocoder';

CREATE EXTENSION IF NOT EXISTS postgis_topology WITH SCHEMA topology;
COMMENT ON EXTENSION postgis_topology IS 'PostGIS topology spatial types and functions';


SET default_tablespace = '';
SET default_table_access_method = heap;


CREATE TABLE IF NOT EXISTS public.counties (
    id bigint NOT NULL,
    ansi character varying(255),
    fips character varying(255),
    name character varying(255),
    state_id bigint NOT NULL
);
ALTER TABLE public.counties OWNER TO flexone;

CREATE SEQUENCE public.counties_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER TABLE public.counties_id_seq OWNER TO flexone;
ALTER SEQUENCE public.counties_id_seq OWNED BY public.counties.id;



CREATE TABLE IF NOT EXISTS public.county_geo_locations (
    geo_location_id bigint,
    county_id bigint NOT NULL
);
ALTER TABLE public.county_geo_locations OWNER TO flexone;


CREATE TABLE IF NOT EXISTS public.geo_locations (
    id bigint NOT NULL,
    geom public.geometry,
    properties character varying(1000)
);
ALTER TABLE public.geo_locations OWNER TO flexone;


CREATE SEQUENCE public.geo_locations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER TABLE public.geo_locations_id_seq OWNER TO flexone;


ALTER SEQUENCE public.geo_locations_id_seq OWNED BY public.geo_locations.id;



CREATE TABLE IF NOT EXISTS public.lake_geo_locations (
    geo_location_id bigint,
    lake_id bigint NOT NULL
);
ALTER TABLE public.lake_geo_locations OWNER TO flexone;


CREATE TABLE IF NOT EXISTS public.lakes (
    id bigint NOT NULL,
    county_fips character varying(255),
    local_id character varying(255),
    name character varying(255),
    nearest_town character varying(255)
);
ALTER TABLE public.lakes OWNER TO flexone;


CREATE SEQUENCE public.lakes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER TABLE public.lakes_id_seq OWNER TO flexone;


ALTER SEQUENCE public.lakes_id_seq OWNED BY public.lakes.id;



CREATE TABLE IF NOT EXISTS public.state_geo_locations (
    geo_location_id bigint,
    state_id bigint NOT NULL
);
ALTER TABLE public.state_geo_locations OWNER TO flexone;


CREATE TABLE IF NOT EXISTS public.states (
    id bigint NOT NULL,
    abbreviation character varying(255),
    ansi character varying(255),
    fips character varying(255),
    name character varying(255)
);
ALTER TABLE public.states OWNER TO flexone;


CREATE SEQUENCE public.states_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER TABLE public.states_id_seq OWNER TO flexone;


ALTER SEQUENCE public.states_id_seq OWNED BY public.states.id;



ALTER TABLE ONLY public.counties ALTER COLUMN id SET DEFAULT nextval('public.counties_id_seq'::regclass);



ALTER TABLE ONLY public.geo_locations ALTER COLUMN id SET DEFAULT nextval('public.geo_locations_id_seq'::regclass);



ALTER TABLE ONLY public.lakes ALTER COLUMN id SET DEFAULT nextval('public.lakes_id_seq'::regclass);



ALTER TABLE ONLY public.states ALTER COLUMN id SET DEFAULT nextval('public.states_id_seq'::regclass);



ALTER TABLE ONLY public.counties
    ADD CONSTRAINT counties_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.county_geo_locations
    ADD CONSTRAINT county_geo_locations_pkey PRIMARY KEY (county_id);



ALTER TABLE ONLY public.geo_locations
    ADD CONSTRAINT geo_locations_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.lake_geo_locations
    ADD CONSTRAINT lake_geo_locations_pkey PRIMARY KEY (lake_id);



ALTER TABLE ONLY public.lakes
    ADD CONSTRAINT lakes_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.state_geo_locations
    ADD CONSTRAINT state_geo_locations_pkey PRIMARY KEY (state_id);



ALTER TABLE ONLY public.states
    ADD CONSTRAINT states_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.state_geo_locations
    ADD CONSTRAINT fk5ns825sdaxwr8vt5r9tlrv25g FOREIGN KEY (geo_location_id) REFERENCES public.geo_locations(id);



ALTER TABLE ONLY public.lake_geo_locations
    ADD CONSTRAINT fketalfpvp42u18ripfcg0347ha FOREIGN KEY (geo_location_id) REFERENCES public.geo_locations(id);



ALTER TABLE ONLY public.county_geo_locations
    ADD CONSTRAINT fko2wta6dqu4q8s0h5ffdd8ooyv FOREIGN KEY (geo_location_id) REFERENCES public.geo_locations(id);



ALTER TABLE ONLY public.state_geo_locations
    ADD CONSTRAINT fkowoye3lo77k638l513f85c14v FOREIGN KEY (state_id) REFERENCES public.states(id);



ALTER TABLE ONLY public.county_geo_locations
    ADD CONSTRAINT fkr0rflrwp9hoftduswew1rcdfh FOREIGN KEY (county_id) REFERENCES public.counties(id);



ALTER TABLE ONLY public.lake_geo_locations
    ADD CONSTRAINT fksy7x096tdgin6gm8y0byo4rnt FOREIGN KEY (lake_id) REFERENCES public.lakes(id);



ALTER TABLE ONLY public.counties
    ADD CONSTRAINT fkt9r2d00r3fjs8jl76tl8c7yjn FOREIGN KEY (state_id) REFERENCES public.states(id);




