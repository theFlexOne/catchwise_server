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
