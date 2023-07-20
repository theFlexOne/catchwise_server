DROP MATERIALIZED VIEW IF EXISTS public.lakes_map_markers;

CREATE MATERIALIZED VIEW public.lakes_map_markers
as select id, name, map_marker from lakes;


DROP MATERIALIZED VIEW IF EXISTS public.water_access_map_markers;

CREATE MATERIALIZED VIEW public.water_access_map_markers
as select id, name, map_marker from water_accesses;