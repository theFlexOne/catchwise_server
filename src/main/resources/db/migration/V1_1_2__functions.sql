DROP FUNCTION IF EXISTS public.map_markers (marker_type varchar);

CREATE FUNCTION public.map_markers (marker_type varchar)
    RETURNS TABLE (
        id bigint,
        name varchar,
        geom geometry,
        type text
    )
    AS $BODY$
DECLARE
    query text;
BEGIN
    IF marker_type ~* '^lakes?$' THEN
        query := 'SELECT id, name, geom, ''lake'' as type FROM lakes_map_markers';
    ELSIF marker_type ~* '^water_access(es)?$' THEN
        query := 'SELECT id, name, geom, ''water_access'' as type FROM water_accesses_map_markers';
    ELSE
        RETURN;
    END IF;
    RETURN QUERY EXECUTE query;
END;
$BODY$
LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS public.f1_get_lakes_with_fish_species (species_name character varying);

CREATE OR REPLACE FUNCTION public.f1_get_lakes_with_fish_species (species_name character varying)
    RETURNS TABLE (
        lake_id bigint,
        lake_name character varying,
        geom geometry)
    LANGUAGE plpgsql
    AS $function$
BEGIN
    RETURN QUERY
    SELECT
        l.id AS lake_id,
        l.name AS lake_name,
        st_centroid (l.geom) AS geom
    FROM
        lakes AS l
        JOIN lakes_fish_species lfs ON l.id = lfs.lake_id
        JOIN fish_species fs ON lfs.fish_species_id = fs.id
    WHERE
        l.name IS NOT NULL
        AND LOWER(fs.name) = LOWER(species_name);
END;
$function$;

DROP FUNCTION IF EXISTS get_surrounding_lakes (lng numeric, lat numeric);

CREATE OR REPLACE FUNCTION public.f1_get_surrounding_lakes (lng numeric, lat numeric)
    RETURNS TABLE (
        lake_id bigint,
        lake_name character varying,
        geom geometry)
    LANGUAGE plpgsql
    AS $function$
BEGIN
    RETURN QUERY WITH pre_filtered_lakes AS (
        SELECT
            id,
            name,
            marker
        FROM
            lakes
        WHERE
            name IS NOT NULL
            AND marker && ST_MakeEnvelope (lng - 1, lat - 1, lng + 2, lat + 2, 4326))
    SELECT
        l.id AS lake_id,
        l.name AS lake_name,
        l.marker AS geom
    FROM
        pre_filtered_lakes l
        INNER JOIN lakes_fish_species lfs ON l.id = lfs.lake_id
        INNER JOIN fish_species fs ON fs.id = lfs.fish_species_id
    GROUP BY
        l.id,
        l.name,
        l.marker;
END;
$function$;

DROP FUNCTION IF EXISTS public.f1_lakes_in_range_from_meters_in_miles (lng double precision, lat double precision, RANGE bigint);

CREATE OR REPLACE FUNCTION public.f1_lakes_in_range_from_meters_in_miles (lng double precision, lat double precision, RANGE bigint)
    RETURNS TABLE (
        id bigint,
        name character varying,
        geom geometry,
        miles double precision)
    LANGUAGE plpgsql
    AS $function$
DECLARE
    point_geog geography;
BEGIN
    point_geog := st_setsrid (st_makepoint (lng, lat), 4326)::geography;
    RETURN QUERY
    SELECT
        l.id,
        l.name,
        l.geom,
        public.F1_TRUNC ((F1_Meters_To_Miles (ST_Distance (l.geom, st_setsrid (st_makepoint (lng, lat), 4326)))), 3) AS miles
    FROM
        lakes l
    WHERE
        ST_DWithin (l.geom, st_setsrid (st_makepoint (lng, lat), 4326), RANGE)
    ORDER BY
        miles;
END;
$function$;

DROP FUNCTION IF EXISTS public.f1_lakes_in_range_from_meters_in_miles_optimized (lng double precision, lat double precision, RANGE bigint);

CREATE OR REPLACE FUNCTION public.f1_lakes_in_range_from_meters_in_miles_optimized (lng double precision, lat double precision, RANGE bigint)
    RETURNS TABLE (
        id bigint,
        name character varying,
        geom geometry,
        miles double precision)
    LANGUAGE plpgsql
    AS $function$
DECLARE
    point geography := st_setsrid (st_makepoint (lng, lat), 4326)::geography;
    box geometry := ST_Expand (point::geometry, RANGE);
BEGIN
    RETURN QUERY
    SELECT
        l.id,
        l.name,
        l.geom,
        public.F1_TRUNC ((F1_Meters_To_Miles (ST_Distance (l.geom::geography, point))), 3) AS miles
    FROM
        lakes l
    WHERE
        l.geom && box
        AND ST_DWithin (l.geom::geography, point, RANGE)
    ORDER BY
        miles;
END;
$function$;

DROP FUNCTION IF EXISTS public.f1_lakes_insert_trigger ();

CREATE OR REPLACE FUNCTION public.f1_lakes_insert_trigger ()
    RETURNS TRIGGER
    LANGUAGE plpgsql
    AS $function$
DECLARE
    map_marker_id integer;
BEGIN
    IF NEW.geom IS NOT NULL AND NEW.map_marker_id IS NULL THEN
        INSERT INTO map_markers (geom)
            VALUES (ST_Centroid (NEW.geom))
        RETURNING
            id INTO map_marker_id;
        NEW.map_marker_id := map_marker_id;
    END IF;
    RETURN NEW;
END;
$function$;

DROP FUNCTION IF EXISTS public.f1_meters_to_miles (meters integer);

CREATE OR REPLACE FUNCTION public.f1_meters_to_miles (meters integer)
    RETURNS double precision
    LANGUAGE plpgsql
    AS $function$
BEGIN
    RETURN (meters * 0.000621371);
END;
$function$;

DROP FUNCTION IF EXISTS public.f1_meters_to_miles (meters double precision);

CREATE OR REPLACE FUNCTION public.f1_meters_to_miles (meters double precision)
    RETURNS double precision
    LANGUAGE plpgsql
    AS $function$
BEGIN
    RETURN meters * 0.000621371192;
END;
$function$;

DROP FUNCTION IF EXISTS public.f1_select_lake_marker ();

CREATE OR REPLACE FUNCTION public.f1_select_lake_marker ()
    RETURNS TABLE (
        id bigint,
        name character varying,
        coordinates geometry)
    LANGUAGE plpgsql
    AS $function$
BEGIN
    RETURN QUERY
    SELECT
        l.id,
        l.name,
        ST_Centroid (l.geom)
    FROM
        lakes l;
END;
$function$;

DROP FUNCTION IF EXISTS public.f1_trunc (number double precision, digits integer);

CREATE OR REPLACE FUNCTION public.f1_trunc (number double precision, digits integer DEFAULT 0)
    RETURNS double precision LANGUAGE plpgsql
    AS $function$
DECLARE
    factor numeric;
BEGIN
    factor := POWER(10, digits);
    RETURN (FLOOR(number * factor) / factor);
END;
$function$;

