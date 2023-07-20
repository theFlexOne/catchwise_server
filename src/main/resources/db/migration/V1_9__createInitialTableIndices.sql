create index if not exists lakes_geom_idx on lakes using gist (geom);
create index if not exists lakes_map_marker_idx on lakes USING gist (map_marker);
create index if not exists lakes_name_idx on lakes (name);

create index if not exists water_accesses_map_marker_idx on water_accesses USING gist (map_marker);
create index if not exists water_accesses_name_idx on water_accesses (name);
