select count(*) "wam count" from map_markers where map_marker_type_id = 2;
select count(*) "wams" from water_accesses;

create table users (
  id serial primary key,
  email varchar(255) not null unique,
  username varchar(255) not null unique,
  password_digest varchar(255) not null,
  created_at timestamp not null default now(),
  updated_at timestamp not null default now()
);

select * from all_markers
where name is not null
and ST_Distance(marker, ST_GeomFromText(format('POINT(%s, %s)', '-94.338468', '46.236647'), 4326)) < 1;

select * from all_markers
where name is not null
and ST_Distance(
    marker,
    ST_GeomFromText(
        format('POINT(%s %s)', '-94.338468', '46.236647'),
        4326
    )
) < 1;



select l.name as lake_name, c.name as county_name, s.name as state_name from lakes l
left join counties c on l.county_id = c.id
left join states s on c.state_id = s.id
left join map_markers m on l.map_marker_id = m.id
where l.name like '%C%'
order by levenshtein(l.name, 'C') asc, ST_Distance(m.geom, ST_GeomFromText(format('POINT(%s %s)', '-94.338468', '46.236647'), 4326)) asc;





alter table map_markers
add column location_id;

select *
from information_schema.columns
where table_name = 'lakes';



-- public.all_markers source
DROP MATERIALIZED VIEW public.all_markers;

CREATE MATERIALIZED VIEW public.all_markers
TABLESPACE pg_default
AS



-- Materialized view to fetch all map locations
DROP MATERIALIZED VIEW IF EXISTS all_map_locations;


DROP FUNCTION get_locations_near_point;



-- select all markers from saint paul
select aml.*, (
    select st_distance(st_transform(aml.marker, 3857), ST_Transform(ST_GeomFromText(format('POINT(%s %s)', '-94.3368', '46.2360'), 4326), 3857)) / 1000
) as km_from_point
from all_map_locations as aml
where st_dwithin(aml.marker, ST_GeomFromText(format('POINT(%s %s)', '-94.3368', '46.2360'), 4326), 1)
and aml.marker_type = 'water_access'
order by km_from_point asc;

select * from get_locations_near_point(-94.3368, 46.2360);

delete from v2.water_accesses wa
where wa.id in (
    select wa.id from v2.water_accesses wa
    left join v2.lakes l on l.id = wa.lake_id
    where l.id is null
);



select * from lakes where id = 2482;








