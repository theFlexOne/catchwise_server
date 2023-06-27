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


CREATE TABLE public.counties (
    id bigint NOT NULL,
    ansi character varying(255),
    fips character varying(255),
    geom public.geometry(MultiPolygon,4326),
    name character varying(255),
    state_id bigint NOT NULL
);


--
-- TOC entry 306 (class 1259 OID 87239)
-- Name: counties_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.counties_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5795 (class 0 OID 0)
-- Dependencies: 306
-- Name: counties_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.counties_id_seq OWNED BY public.counties.id;


--
-- TOC entry 309 (class 1259 OID 87249)
-- Name: fish_species; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.fish_species (
    id bigint NOT NULL,
    description character varying(10000),
    family character varying(255),
    genus character varying(255),
    identification character varying(10000),
    name character varying(255),
    species character varying(255)
);


--
-- TOC entry 308 (class 1259 OID 87248)
-- Name: fish_species_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.fish_species_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5796 (class 0 OID 0)
-- Dependencies: 308
-- Name: fish_species_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.fish_species_id_seq OWNED BY public.fish_species.id;


--
-- TOC entry 311 (class 1259 OID 87258)
-- Name: lakes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.lakes (
    id bigint NOT NULL,
    county_fips character varying(255),
    geom public.geometry(Point,4326),
    local_id character varying(255),
    name character varying(255),
    nearest_town character varying(255)
);


--
-- TOC entry 312 (class 1259 OID 87266)
-- Name: lakes_fish_species; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.lakes_fish_species (
    lake_id bigint NOT NULL,
    fish_species_id bigint NOT NULL
);


--
-- TOC entry 310 (class 1259 OID 87257)
-- Name: lakes_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.lakes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5797 (class 0 OID 0)
-- Dependencies: 310
-- Name: lakes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.lakes_id_seq OWNED BY public.lakes.id;


--
-- TOC entry 313 (class 1259 OID 87269)
-- Name: other_fish_species_names; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.other_fish_species_names (
    fish_species_id bigint NOT NULL,
    other_names character varying(255)
);


--
-- TOC entry 315 (class 1259 OID 87273)
-- Name: states; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.states (
    id bigint NOT NULL,
    abbreviation character varying(255),
    ansi character varying(255),
    fips character varying(255),
    geom public.geometry(MultiPolygon,4326),
    name character varying(255)
);


--
-- TOC entry 314 (class 1259 OID 87272)
-- Name: states_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.states_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5798 (class 0 OID 0)
-- Dependencies: 314
-- Name: states_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.states_id_seq OWNED BY public.states.id;


--
-- TOC entry 5599 (class 2604 OID 87243)
-- Name: counties id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.counties ALTER COLUMN id SET DEFAULT nextval('public.counties_id_seq'::regclass);


--
-- TOC entry 5600 (class 2604 OID 87252)
-- Name: fish_species id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fish_species ALTER COLUMN id SET DEFAULT nextval('public.fish_species_id_seq'::regclass);


--
-- TOC entry 5601 (class 2604 OID 87261)
-- Name: lakes id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.lakes ALTER COLUMN id SET DEFAULT nextval('public.lakes_id_seq'::regclass);


--
-- TOC entry 5602 (class 2604 OID 87276)
-- Name: states id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.states ALTER COLUMN id SET DEFAULT nextval('public.states_id_seq'::regclass);


--
-- TOC entry 5627 (class 2606 OID 87247)
-- Name: counties counties_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.counties
    ADD CONSTRAINT counties_pkey PRIMARY KEY (id);


--
-- TOC entry 5629 (class 2606 OID 87256)
-- Name: fish_species fish_species_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fish_species
    ADD CONSTRAINT fish_species_pkey PRIMARY KEY (id);


--
-- TOC entry 5631 (class 2606 OID 87265)
-- Name: lakes lakes_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.lakes
    ADD CONSTRAINT lakes_pkey PRIMARY KEY (id);


--
-- TOC entry 5633 (class 2606 OID 87280)
-- Name: states states_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.states
    ADD CONSTRAINT states_pkey PRIMARY KEY (id);


--
-- TOC entry 5637 (class 2606 OID 87296)
-- Name: other_fish_species_names fk76iivc3y0si1qgya5snr7ta81; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.other_fish_species_names
    ADD CONSTRAINT fk76iivc3y0si1qgya5snr7ta81 FOREIGN KEY (fish_species_id) REFERENCES public.fish_species(id);


--
-- TOC entry 5635 (class 2606 OID 87286)
-- Name: lakes_fish_species fkaucfwekqyni9h3n7q81e9hfw0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.lakes_fish_species
    ADD CONSTRAINT fkaucfwekqyni9h3n7q81e9hfw0 FOREIGN KEY (fish_species_id) REFERENCES public.fish_species(id);


--
-- TOC entry 5636 (class 2606 OID 87291)
-- Name: lakes_fish_species fkglf98poj4jldaoc170qi0qa5p; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.lakes_fish_species
    ADD CONSTRAINT fkglf98poj4jldaoc170qi0qa5p FOREIGN KEY (lake_id) REFERENCES public.lakes(id);


--
-- TOC entry 5634 (class 2606 OID 87281)
-- Name: counties fkt9r2d00r3fjs8jl76tl8c7yjn; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.counties
    ADD CONSTRAINT fkt9r2d00r3fjs8jl76tl8c7yjn FOREIGN KEY (state_id) REFERENCES public.states(id);


-- Completed on 2023-06-12 20:36:36

--
-- PostgreSQL database dump complete
--

