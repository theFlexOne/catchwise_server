DROP MATERIALIZED VIEW IF EXISTS public.lakes_map_markers;
CREATE MATERIALIZED VIEW public.lakes_map_markers
as select id, name, map_marker as geom from lakes;

DROP MATERIALIZED VIEW IF EXISTS public.water_accesses_map_markers;
CREATE MATERIALIZED VIEW public.water_accesses_map_markers
as select id, name, map_marker as geom from water_accesses;

DROP FUNCTION IF EXISTS public.f1_map_markers(marker_type varchar);
CREATE FUNCTION public.f1_map_markers(marker_type varchar)
RETURNS TABLE(id bigint, name varchar, geom geometry, type text) AS
$BODY$
DECLARE
    query text;
BEGIN
    IF marker_type ~* '^lakes?$' THEN
        query := 'SELECT id, name, geom, ''lake'' as type FROM lakes_map_markers';
    ELSIF marker_type ~* '^water_access(es)?$' THEN
        query := 'SELECT id, name, geom, ''water_access'' as type FROM water_accesses_map_markers';
    else
    	return;
    END IF;

    RETURN QUERY EXECUTE query;
END;
$BODY$
LANGUAGE plpgsql;